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
package uk.org.gencoreoperative.jira;

import com.beust.jcommander.JCommander;
import com.google.gson.Gson;

import uk.org.gencoreoperative.jira.config.Config;
import uk.org.gencoreoperative.jira.rest.RESTClient;

/**
 * Demonstration client that will print the exported JIRA {@link uk.org.gencoreoperative.jira.model.Issue} as
 * JSON on the {@code stdout}.
 */
public class Main {
    private static final Gson GSON = new Gson();
    public static void main(String... args) {
        Config object = new Config();
        JCommander commander = new JCommander(object);
        commander.setProgramName("jira-export");
        commander.parse(args);

        int exit = 1;
        try {
            if (object.isHelp()) {
                commander.usage();
            } else {
                RESTClient client = new RESTClient(object);
                client.stream().map(GSON::toJson).forEach(System.out::println);
            }
            exit = 0;
        } catch (Throwable e) {
            System.err.println(e.getMessage());
        } finally {
            System.exit(exit);
        }
    }
}
