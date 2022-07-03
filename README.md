# CUSTOMER CONTACTS SERVICE

A simple RESTful microservice with in-memory datastore. 

## System Prerequistes

    1. Java 17  (Built with Zulu Jdk 17 )
    2. IDE ( Any IDE that supports Annotation processors )
    3. Lombok setup based on your IDE (annotation processing should be enabled )
    4. If you are behind proxies or enterprise artifactories, setup them in build.gradle.
    5. Gradle wrapper and Spring boot are used.

## Design Notes

* Service is built using Spring Boot and uses base path as `/api/v1`.
* APIs are designed using RESTful guidelines.
* Minimalistic validations are used for demonstration purpose. 
* Service layer is not used as business rules are minimal for this use-case. 
* A simple in-memory data structure is used to store data.
* Default logback properties are used for logging.
* Service uses actuator with default functionality.  use `/actuator` for all the exposed endpoints.
* Service uses http protocol and 8080 as the default port.

## Assumptions
    1. No authentication/authorisation is considered.
    2. Considering the time limitations, pagination is not implemented for `Get all phone numbers` API.
    3. Besides the required action "Activate", additonal phone actions are assumed for demonstraiton purpose.
    4. Few additional properties are assumed and added to resources.


## How to run the application?

Project includes gradle wrapper.

* To build, use `./gradlew clean build`
* To run the spring boot app, use `./gradlew bootRun`. Make sure 8080 is not used by any other process.
* If using IDE, run [CustomerContactsServiceApplication.main()](./src/main/java/com/telco/customer/contacts/CustomerContactsServiceApplication.java);

## How to run tests?

* Run tests using `./gradlew test` and check the report at `./build/reports/tests/test/index.html`
* Run coverage using `./gradlew jacocoTestReport` and check the report at `./build/reports/jacocoHtml/index.html`

