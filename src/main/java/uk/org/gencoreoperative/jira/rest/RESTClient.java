/**
 * MIT License
 * <p>
 * Copyright (c) 2024 GencoreOperative
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package uk.org.gencoreoperative.jira.rest;

import static java.net.HttpURLConnection.HTTP_OK;
import static java.text.MessageFormat.format;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.gson.Gson;

import lombok.NonNull;
import uk.org.gencoreoperative.jira.config.Config;
import uk.org.gencoreoperative.jira.model.Issue;
import uk.org.gencoreoperative.jira.model.SearchResults;

/**
 * A REST based client for querying JIRA and returning a stream of the issues.
 * <p>
 * This client will handle the HTTP basic authentication required to call the JIRA
 * service.
 */
public class RESTClient {
    public static final int WINDOW = 100;
    private static final Gson GSON = new Gson();
    private final Config config;
    private final String authHeader;

    public RESTClient(Config config) {
        this.config = config;
        authHeader = getBasicAuth(this.config);
    }

    /**
     * Stream the issues from JIRA by performing a query to work out how many results
     * there are, and then a stream that will perform those queries as they are consumed
     * from the stream.
     *
     * @return A non-null {@link Stream} of the issues found in JIRA that matched the
     * configured JQL.
     */
    public Stream<Issue> stream() {
        int count = countResults();
        List<URL> urls = new ArrayList<>();
        for (int i = 0; i < count; i+= WINDOW) {
            urls.add(getSeachURL(i, WINDOW));
        }
        return urls.stream()
                .map(this::performQuery)
                .map(jsonString -> GSON.fromJson(jsonString, SearchResults.class))
                .flatMap(searchResults -> searchResults.getIssues().stream());
    }

    /**
     * Count the number of results by performing a query but not returning any results.
     * @return Zero or more indicating the total number of results.
     */
    private int countResults() {
        String json = performQuery(getSeachURL(0, 0));
        SearchResults result = GSON.fromJson(json, SearchResults.class);
        return result.getTotal();
    }

    private URL getSeachURL(int startAt, int maxResults) {
        try {
            return new URL(format("{0}/rest/api/2/search?jql={1}&fields={2}&startAt={3}&maxResults={4}",
                    config.getServer().toString(),
                    URLEncoder.encode(config.getJql(), "UTF8"), // Java 8 approach
                    String.join(",", config.getFields()),
                    Integer.toString(startAt),
                    maxResults));
        } catch (MalformedURLException | UnsupportedEncodingException e) {
            throw new IllegalArgumentException("Invalid URL Format using Config:\n" + config, e);
        }
    }

    /**
     * Perform an HTTP GET Request and return the contents as a String.
     *
     * @param url The URL to perform a GET request with.
     * @return The contents of the request, in JSON format.
     * @throws RuntimeException If there was an error performing the query. Error message contents
     * will be included in the exception where possible.
     */
    private String performQuery(@NonNull final URL url) {
        try {
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Authorization", authHeader);
            conn.setRequestProperty("Content-Type", "application/json");
            int response = conn.getResponseCode();
            if (response == HTTP_OK) {
                return readStream(conn.getInputStream());
            }
            throw new RuntimeException(format("Could not perform query {0} ({1}):\n{2}",
                    url, response, readStream(conn.getErrorStream())));
        } catch (IOException e) {
            throw new RuntimeException("Failed to connect: " + url, e);
        }
    }

    private String readStream(InputStream stream) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream))) {
            return reader.lines().collect(Collectors.joining("\n"));
        }
    }

    /**
     * Generates the HTTP Basic Authentication formatted credentials needed for the request.
     * @param config Config containing the credentials.
     * @return Basic Authentication String.
     */
    private static String getBasicAuth(@NonNull final Config config) {
        String auth = format("{0}:{1}", config.getUsername(), new String(config.getPassword()));
        byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes(StandardCharsets.UTF_8));
        return "Basic " + new String(encodedAuth);
    }
}
