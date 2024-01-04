# JIRARestClient

## Overview

JIRARestClient is simple and limited Java client for interacting with JIRA (Atlassian's issue and project tracking 
software) using the [v2 REST API](https://developer.atlassian.com/server/jira/platform/jira-rest-api-version-2-tutorial-8946379/). 
This allows you to export JIRA Issues  in both POJO and JSON formats. We have provided a JSON export function to
allow us to demonstrate the client on the command line.

*Note:* The client only supports the Summary, Description, and Components fields in JIRA. It could easily be expanded to 
include more fields.

## Configuration

The `Config` class details the configuration options that are required for this client to function. These are then
used by the command line example class `Main`.

In order export JIRA issues, we need to provide the following:

* The URL of the server that we are exporting from.
* The authentication credentials in the form of a username and password file.
* The [JQL (JIRA Query Language)](https://support.atlassian.com/jira-service-management-cloud/docs/use-advanced-search-with-jira-query-language-jql/) statement used to select the issues to export.
* The fields that we are exporting from the Issue.

Once these are provided, the client will perform the query and then page through the results printing the
JSON output.

## Usage

We can access the project programmatically by invoking the `RESTClient` with an initialised `Config` instance, 
or we can run the project from the command line using the `Main` class. The following examples demonstrate this
approach.

Help information can be obtained from the `--help` option:
```
$ java -jar jira-export.jar --help
Usage: jira-export [options]
  Options:
    -f, --fields
      The fields from JIRA to extract from the JIRA Issue. If this is not 
      specified then all fields will be extracted.
    -h, --help
      Shows this help information
  * -j, --jql
      The JQL statement to query the server with. See 
      https://www.atlassian.com/software/jira/guides/jql/overview#what-is-jql 
      for more information.
  * -p, --passwordFile
      The path of the file containing the JIRA password. Must be read-only for 
      the user.
  * -s, --serverURL
      The server URL of the JIRA server.
  * -u, --username
      Username to authenticate to JIRA with.
```

Next, we can use this utility to perform a query against a JIRA server with the following example.
You will need to provide your own authentication credentials, Server URL, and JQL statement.
```
echo "<password>" > .password; chmod 400 .password
java -jar jira-export.jar \
    -u <username> \
    -p .password \
    -s https://your.server.here/jira \
    -j "type = Bug AND created >= startOfWeek(-1)"
```

## Dependencies

The project is a Java project and is designed to work on Java 8 and above. The project will package the
runtime dependencies into a single Jar file for ease of deployment.

*Runtime*
- [**com.google.code.gson:gson**](https://mvnrepository.com/artifact/com.google.code.gson/gson): JSON serialization and deserialization library.
- [**com.beust:jcommander**](https://mvnrepository.com/artifact/com.beust/jcommander): Command line argument parsing library.

*Compile*
- [**org.projectlombok:lombok**](https://projectlombok.org/): Java library that helps reduce boilerplate code (getters, setters, constructors, etc.).
- [**com.google.code.findbugs:jsr305**](https://mvnrepository.com/artifact/com.google.code.findbugs/jsr305): Annotations for software defect detection.

*Testing*
- [**junit:junit**](https://mvnrepository.com/artifact/junit/junit): Unit testing framework for Java.
- [**org.assertj:assertj-core**](https://mvnrepository.com/artifact/org.assertj/assertj-core): Fluent assertions library for Java.
- [**org.forgerock.cuppa:cuppa**](https://mvnrepository.com/artifact/org.forgerock.cuppa/cuppa): Lightweight testing framework for Java.
- [**org.forgerock.cuppa:cuppa-junit**](https://mvnrepository.com/artifact/org.forgerock.cuppa/cuppa-junit): Integration of Cuppa with JUnit.
- [**org.mockito:mockito-all**](https://mvnrepository.com/artifact/org.mockito/mockito-all): Mocking framework for unit tests in Java.