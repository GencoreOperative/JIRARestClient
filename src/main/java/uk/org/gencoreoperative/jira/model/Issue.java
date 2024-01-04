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
package uk.org.gencoreoperative.jira.model;

import java.net.URL;

import lombok.Getter;
import lombok.ToString;

/**
 * Models the fields we need from the JIRA Issue.
 * <pre>
 *             "expand": "operations,versionedRepresentations,editmeta,changelog,renderedFields",
 *             "id": "120937",
 *             "self": "https://xyz.org/jira/rest/api/2/issue/120937",
 *             "key": "OPENAM-16598",
 *             "fields":
 *             {
 *                 "summary": "Support validation of encrypted id_tokens in Social OIDC Authentication Node",
 *                 "components":
 *                 [
 *                     {
 *                         "self": "https://xyz.org/jira/rest/api/2/component/11130",
 *                         "id": "11130",
 *                         "name": "OpenID Connect"
 *                     },
 *                     {
 *                         "self": "https://xyz.org/jira/rest/api/2/component/12961",
 *                         "id": "12961",
 *                         "name": "trees"
 *                     }
 *                 ]
 *             }
 * </pre>
 */
@Getter
@ToString
public class Issue {
    private Integer id;
    private URL self;
    private String key;
    private Field fields;
}
