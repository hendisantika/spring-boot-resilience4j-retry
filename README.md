## Resilience4j Retry: Building Fault-Tolerant Spring Boot Applications

[![Java CI with Maven](https://github.com/hendisantika/spring-boot-resilience4j-retry/actions/workflows/maven.yml/badge.svg)](https://github.com/hendisantika/spring-boot-resilience4j-retry/actions/workflows/maven.yml)
![Java](https://img.shields.io/badge/Java-25-orange?logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.1.x-brightgreen?logo=springboot)
![Resilience4j](https://img.shields.io/badge/Resilience4j-2.4.0-blue)
![Dependabot](https://img.shields.io/badge/Dependabot-enabled-025E8C?logo=dependabot)

## spring-boot-resilience4j-retry
A simple app highlighting how we can implement retry mechanism using Resilience4j in Spring Boot

## Tech Stack

| Component            | Version / Notes                                                            |
|----------------------|----------------------------------------------------------------------------|
| Java                 | **JDK 25** (Temurin in CI)                                                 |
| Spring Boot          | 4.1.x (`spring-boot-starter-web`, `-actuator`, `-aspectj`, `-restclient`)  |
| Resilience4j         | 2.4.0 (`resilience4j-spring-boot4`)                                        |
| Build tool           | Maven (wrapper included: `./mvnw`)                                         |
| Lombok               | Managed by Spring Boot, wired as an explicit annotation processor          |

## Prerequisites

- JDK 25 (`java -version` should print `25`)
- No local Maven needed, use the bundled Maven Wrapper

## Build, Test & Run

```shell
# build & run tests
./mvnw clean verify

# run the application (http://localhost:8080)
./mvnw spring-boot:run

# or run the packaged jar
./mvnw clean package
java -jar target/resilience4j-retry-0.0.1-SNAPSHOT.jar
```

> The mock "external" API is served by the same app at `http://localhost:8080/mock/movies/{id}`,
> so keep the application on port **8080**.

### Actuator

All actuator endpoints are exposed. Useful ones for this demo:

```shell
curl http://localhost:8080/actuator/health
curl http://localhost:8080/actuator/retries          # registered retry instances
curl http://localhost:8080/actuator/retryevents      # retry events (attempts, success, error)
```

## App Overview

This is a simple app wherein we are fetching the movie details based on the movie id. The movie details are fetched from
external service that is called using the Spring Rest Template. For simplicity, we have created a mock controller which
acts as a external service for returning the movie details.

## Retry Scenarios

We have created a single controller endpoint which accepts movie id as path parameter and query parameter retryType
which accepts predefined set of values to mimic the different retry examples.

### Acceptable Values

#### For Path Parameter - Movie Id

a. **1** or **2** - Mock controller returns valid movie information<br/>
b. **3** - Mock controller returns HTTP status code 404<br/>
c. **4** or **any other numeric value** - Mock controller returns null which leads to MovieNotFound Exception

#### For Query Parameter - retryType

Different retry instances are defined inside the application.yml. To mimic different retry scenarios use:<br/>
a. **simple-retry:** simpleRetry retry instance will be triggered<br/>
b. **retry-on-exception:** retryOnException retry instance will be triggered.<br/>
c. **retry-on-exception-predicate:** retryBasedOnExceptionPredicate retry instance will be triggered.<br/>
d. **retry-on-conditional-predicate:** retryBasedOnConditionalPredicate retry instance will be triggered.<br/>
e. **retry-using-exponential-backoff:** retryUsingExponentialBackoff retry instance will be triggered.<br/>
f. **retry-using-randomized-wait:** retryUsingRandomizedWait retry instance will be triggered.<br/>
g. **retry-with-fallback:** simpleRetry retry instance will be triggered and fallback method logic will be executed in
this case.<br/>
h. **retry-with-custom-config:** customRetryConfig retry instance defined in RetryConfiguration class will be
triggered.<br/>
i. **retry-with-event-details:** retryWithEventDetails retry instance will be triggered.<br/>

## cURL Commands

Check the application logs in order to get the better understanding of different retry scenarios.

### 1. Simple Retry

```
curl 'http://localhost:8080/movies/3?retryType=simple-retry'
```

### 2. Retry on Configured Exceptions

```
curl 'http://localhost:8080/movies/3?retryType=retry-on-exception'
```

### 3. Retry on Exception Predicate

```
curl 'http://localhost:8080/movies/4?retryType=retry-on-exception-predicate'
```

### 4. Retry on Conditional Predicate

```
curl 'http://localhost:8080/movies/4?retryType=retry-on-conditional-predicate'
```

### 5. Retry Using Exponential Backoff

```
curl 'http://localhost:8080/movies/3?retryType=retry-using-exponential-backoff'
```

### 6. Retry Using Randomized Wait

```
curl 'http://localhost:8080/movies/3?retryType=retry-using-randomized-wait'
```

### 7. Retry with Fallback

```
curl 'http://localhost:8080/movies/4?retryType=retry-with-fallback'
```

### 8. Retry with Custom Retry Configuration

```
curl 'http://localhost:8080/movies/3?retryType=retry-with-custom-config'
```

### 9. Retry with Event Details

```
curl 'http://localhost:8080/movies/3?retryType=retry-with-event-details'
```

## CI/CD & Dependency Management

- **GitHub Actions** (`.github/workflows/maven.yml`): builds and tests the project with JDK 25 on every push and pull
  request to `main`, then submits the Maven dependency graph to GitHub (push only) for better Dependabot alerts.
- **Dependabot** (`.github/dependabot.yml`):
  - **Maven**: checked daily at 05:00 (Asia/Jakarta), updates grouped into `spring-boot`, `resilience4j`,
    `test-dependencies` and `maven-patch-minor`, labelled `dependencies` + `java`, commit prefix `chore(deps)`.
  - **GitHub Actions**: checked weekly (Monday 05:00 Asia/Jakarta), all action bumps grouped in one PR, labelled
    `dependencies` + `github-actions`, commit prefix `ci(deps)`.
