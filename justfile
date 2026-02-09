# Java Faker justfile
# https://github.com/casey/just

# Default recipe - show available recipes
default:
    @just --list

# Build the project (compile only)
build:
    ./mvnw compile -q

# Run all tests
test:
    ./mvnw test

# Run a specific test class (e.g., just test-class FakerTest)
test-class CLASS:
    ./mvnw test -Dtest={{CLASS}}

# Run tests with coverage report
coverage:
    ./mvnw test jacoco:report
    @echo "Coverage report: target/site/jacoco/index.html"

# Clean build artifacts
clean:
    ./mvnw clean -q

# Full clean build
rebuild: clean build

# Package the JAR
package:
    ./mvnw package -DskipTests -q

# Install to local Maven repository
install:
    ./mvnw install -DskipTests -q

# Run OpenRewrite recipes (dry run)
rewrite-dry-run:
    ./mvnw rewrite:dryRun

# Run OpenRewrite recipes (apply changes)
rewrite-run:
    ./mvnw rewrite:run

# Show dependency tree
deps:
    ./mvnw dependency:tree

# Check for dependency updates
updates:
    ./mvnw versions:display-dependency-updates

# Verify the project (compile, test, package)
verify:
    ./mvnw verify

# Generate Javadoc
javadoc:
    ./mvnw javadoc:javadoc
    @echo "Javadoc: target/site/apidocs/index.html"
