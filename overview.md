# Java Faker Codebase Overview

This is **Java Faker**, a data generation library for creating realistic fake data (names, addresses, phone numbers, etc.) for testing and development. It's a Java port of Ruby's Faker gem.

## Project Info
- **Version**: 1.0.3-SNAPSHOT
- **Build**: Maven (with wrapper)
- **Java**: 11+
- **License**: Apache 2.0

## Structure
```
src/main/java/com/github/javafaker/
├── Faker.java              # Main entry point (facade)
├── *.java                  # 93 data provider classes
├── service/                # Core infrastructure
│   ├── FakeValuesService   # Resolves YAML data, expressions
│   ├── FakeValues          # Loads locale YAML files
│   └── RandomService       # Random number generation
└── idnumbers/              # ID generators (SSN, Swedish IDs)

src/main/resources/
└── *.yml                   # 59 locale data files (en, de, fr, ja, etc.)

src/test/java/              # 111 test files
```

## Key Components

| Component | Purpose |
|-----------|---------|
| **Faker** | Central facade with 93+ provider instances |
| **FakeValuesService** | Resolves YAML expressions, handles locale chains |
| **RandomService** | Random numbers, hex, UUIDs |
| **Data Providers** | Name, Address, Internet, Company, Lorem, GameOfThrones, Pokemon, etc. |

## Dependencies
- **Guava** (33.5.0) - Utilities
- **Commons Lang3** (3.20.0) - String utils
- **SnakeYAML** (2.5) - YAML parsing
- **Generex** (1.0.2) - Regex-to-string generation

## How It Works
1. `Faker` instantiates all provider classes
2. Providers call `faker.fakeValuesService().resolve("key")`
3. `FakeValuesService` looks up data in locale YAML files
4. YAML expressions like `#{Name.first_name}` are evaluated dynamically
5. Results can be transformed with `numerify`, `letterify`, `regexify`

## Testing
- 111 test classes using JUnit 4 + Mockito
- Custom `@Repeat` annotation for multi-run tests
- Integration tests in `integration/` subdirectory
- JaCoCo coverage with Coveralls integration

The codebase is well-organized with clear separation between data providers and the service layer that handles data loading and expression evaluation.
