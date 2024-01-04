# JIRARestClient

## Overview

JIRARestClient is a Java-based client for interacting with JIRA (Atlassian's issue and project tracking software) 
using its REST API. This allows you to export JIRA Issues in both POJO format. For demonstration purposes
this is then converted to JSON for command line access.

## Configuration

The configuration for JIRARestClient is defined in the Config class located in the org.example.config 
package. This class provides a set of parameters that can be customized to suit your JIRA environment.
Parameters

* help: Displays help information for using the client.
* username: Specifies the username for authenticating to JIRA. This parameter is required.
* passwordFile: Specifies the path to the file containing the JIRA password. The file must be read-only for the user. This parameter is required and validated using the PasswordValidator class.
* serverURL: Specifies the server URL of the JIRA server. If not provided, the default value is set to "https://bugster.forgerock.org/jira". This parameter is validated using the URIValidator class.
* jql: Specifies the JIRA Query Language (JQL) to query from the server. If not provided, the default JQL is set to filter issues based on project, issue type, components, and summary.
* fields: Specifies the JIRA fields to extract as text for the FastText output. If not provided, the default fields are set to "components,summary".

## Usage

To use JIRARestClient, create an instance of the Config class and set the desired parameters. You can then pass this 
configuration object to the JIRAClient for interacting with the JIRA server.

### Example usage:

```java
public class Main {
public static void main(String[] args) {
Config config = new Config();
// Set the desired configuration parameters
config.setUsername("your_username");
config.setPasswordFile("/path/to/password/file");
// Set other parameters as needed

        JIRAClient jiraClient = new JIRAClient(config);
        // Use the JIRAClient to interact with the JIRA server
    }
}
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