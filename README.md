# banco-inter-challenge

Challenge [Spring Boot](http://projects.spring.io/spring-boot/) to Banco Inter.

## Requirements

For building and running the application you need:

- [JDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [Maven 3](https://maven.apache.org)

## Running the application locally

There are several ways to run a Spring Boot application on your local machine. One way is to execute the `main` method in the `br.com.bancointer.challenge.ChallengeApplication` class from your IDE.

Alternatively you can use the [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html) like so:

```shell
mvn spring-boot:run
```
To run all Tests you can use Maven, like this: 
```shell
mvn tests
```

## Documentation

You can see our documentation in:

- [Swagger](http://localhost:8080/swagger-ui.html)