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

package uk.org.gencoreoperative.jira.config;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toSet;

import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Scanner;
import java.util.Set;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Strings;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;

/**
 * Configuration object that represents the configuration for the client.
 */
@Getter
@Setter
@NoArgsConstructor
public class Config {
    @Parameter(names = { "-h", "--help" },
            description = "Shows this help information",
            help = true)
    private boolean help;

    @Parameter(names = { "-u", "--username" },
               description = "Username to authenticate to JIRA with.",
               required = true)
    private String username;

    @Parameter(names = { "-p", "--passwordFile" },
               validateValueWith = PasswordValidator.class,
               description = "The path of the file containing the JIRA password. Must be read-only for the user.",
               required = true)
    private String passwordFile;

    @Parameter(names = { "-s", "--serverURL" },
               description = "The server URL of the JIRA server.",
               validateValueWith = URIValidator.class,
                required = true)
    private String server;

    @Parameter(names = { "-j", "--jql" },
               description = "The JQL statement to query the server with. " +
                       "See https://www.atlassian.com/software/jira/guides/jql/overview#what-is-jql for more information.",
               required = true)
    private String jql;

    @Parameter(names = { "-f", "--fields" },
            description = "The fields from JIRA to extract from the JIRA Issue. If this is not specified then all " +
                    "fields will be extracted.")
    private String fields;

    @SneakyThrows
    public URI getServer() {
        return server.endsWith("/") ? new URI(server.substring(0, server.length() - 1)) : new URI(server);
    }

    @SneakyThrows // Should have been validated already
    public char[] getPassword() {
        return new Scanner(Files.newInputStream(Paths.get(passwordFile))).nextLine().toCharArray();
    }

    public Set<String> getFields() {
        return Strings.isStringEmpty(fields) ? Collections.emptySet() : stream(fields.split(",")).collect(toSet());
    }
}
