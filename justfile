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

# Format all Java files using Google Java Format
format:
    ./mvnw spotless:apply

# Check if Java files are properly formatted (for CI/hooks)
format-check:
    ./mvnw spotless:check

# Validate YAML files syntax
yaml-check:
    #!/usr/bin/env bash
    set -euo pipefail
    yaml_files=$(find . -name "*.yml" -o -name "*.yaml" | grep -v ".git" | grep -v "target")
    if [ -z "$yaml_files" ]; then
        echo "No YAML files found"
        exit 0
    fi
    for file in $yaml_files; do
        if ! ./mvnw validate -f "$file" &>/dev/null; then
            # Fallback to basic syntax check using Python or yq if available
            if command -v python3 &>/dev/null; then
                python3 -c "import yaml; yaml.safe_load(open('$file'))" || {
                    echo "YAML validation failed for: $file"
                    exit 1
                }
            elif command -v yq &>/dev/null; then
                yq eval "$file" > /dev/null || {
                    echo "YAML validation failed for: $file"
                    exit 1
                }
            else
                echo "Warning: No YAML validator available (install python3 or yq)"
                exit 0
            fi
        fi
    done
    echo "All YAML files are valid"

# Install git hooks using lefthook
hooks-install:
    lefthook install

# Uninstall git hooks
hooks-uninstall:
    lefthook uninstall
