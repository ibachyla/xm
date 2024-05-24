# Automation testing tasks

<!-- TOC -->
* [Automation testing tasks](#automation-testing-tasks)
  * [Tools & Libraries](#tools--libraries)
  * [How to run](#how-to-run)
    * [Run all tests](#run-all-tests)
    * [Run web UI tests (Task 1) only](#run-web-ui-tests-task-1-only)
    * [Run REST API tests (Task 2) only](#run-rest-api-tests-task-2-only)
  * [Configuration](#configuration)
  * [Structure](#structure)
<!-- TOC -->

## Tools & Libraries

- Java 21
- Gradle 8.7
- Spring Boot 3.2.5
- JUnit 5.10.2
- Selenium 4.20.0
- WebDriverManager 5.8.0
- AssertJ 3.25.1
- REST Assured 5.4.0

## How to run

> Tests functioning was checked on macOS Sonoma 14.4.1 and Chrome 124.0.6367.209 (arm64).

### Run all tests

Mac/Linux:

```shell
./gradlew clean test
```

Windows:

```shell
gradlew clean test
```

### Run web UI tests (Task 1) only

Mac/Linux:

```shell
./gradlew clean test -Ptags=web
```

Windows:

```shell
gradlew clean test -Ptags=web
```

### Run REST API tests (Task 2) only

Mac/Linux:

```shell
./gradlew clean test -Ptags=api
```

Windows:

```shell
gradlew clean test -Ptags=api
```

## Configuration

Configuration parameters (e.g. browser, resolution, etc.) can be found in `src/test/resources/application.yaml`.

## Structure

- `src/test/java/com/github/ibachyla/xm/web` - contains implementation of use cases for Task 1;
- `src/test/java/com/github/ibachyla/xm/api` - contains implementation of use cases for Task 2;
- `src/main/java/com/github/ibachyla/xm` - contains implementation of test logic and utilities.