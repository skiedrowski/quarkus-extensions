

# Quarkus Extensions to make REST Panache data usable with Kotlin

**Work in Progress!**

**State: Unusable**

# What I've done so far
- created a new standalone extension `hibernate-orm-rest-data-panache-kotlin`
- adapted dependencies to use `-kotlin` Variants
- adapted imports
- converted tests of the `deployment` module to Kotlin and added the Kotlin compiler
- found test failing for various reasons

## Using a "one test-failure after another strategy"
I decided to go with Repo-Style first since Active Record additionally introduces to `companion object` problem.
--> issues

# Getting Started
1. Use JDK 17+
2. Build `rest-data-panache-kotlin`
```
cd rest-data-panache-kotlin
mvn clean install
```

3. Build `hibernate-orm-rest-data-panache-kotlin`
```
cd hibernate-orm-rest-data-panache-kotlin
mvn clean package -Dmaven.test.skip=true
```
Skip the tests for now since they fail.

4. Address test failures

# TODOs
... besides issues

- `hibernate-orm-rest-data-panache-kotlin`
  - fix tests
  - ...
- `rest-data-panache-kotlin`
    - `KotlinTypeProvider` -> go back to java only, or use `object`
    - ...
- both extensions
  - TODO comments
  - (Kotlin) code cleanup - autoconverted code compiles but does not look nice
  - add to quarkus or quarkiverse
  - ...

