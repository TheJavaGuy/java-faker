# Execution Flow: `faker.name().fullName()`

```
┌─────────────────────────────────────────────────────────────────────────────┐
│ 1. faker.name()                                                             │
│    Faker.java:374-376                                                       │
│    Returns the pre-instantiated Name object (created in constructor:142)    │
└────────────────────────────────────┬────────────────────────────────────────┘
                                     ▼
┌─────────────────────────────────────────────────────────────────────────────┐
│ 2. name.fullName()                                                          │
│    Name.java:53-55                                                          │
│    Simply delegates to name() method                                        │
└────────────────────────────────────┬────────────────────────────────────────┘
                                     ▼
┌─────────────────────────────────────────────────────────────────────────────┐
│ 3. name.name()                                                              │
│    Name.java:28-30                                                          │
│    Calls: faker.fakeValuesService().resolve("name.name", this, faker)       │
└────────────────────────────────────┬────────────────────────────────────────┘
                                     ▼
┌─────────────────────────────────────────────────────────────────────────────┐
│ 4. FakeValuesService.resolve("name.name", ...)                              │
│    FakeValuesService.java:304-312                                           │
│    - Calls safeFetch("name.name", null) to get expression from YAML         │
│    - Returns one of these patterns (randomly chosen):                       │
│        "#{prefix} #{first_name} #{last_name}"                               │
│        "#{first_name} #{last_name} #{suffix}"                               │
│        "#{first_name} #{last_name}" (4 times - more likely)                 │
└────────────────────────────────────┬────────────────────────────────────────┘
                                     ▼
┌─────────────────────────────────────────────────────────────────────────────┐
│ 5. FakeValuesService.resolveExpression(expression, current, root)           │
│    FakeValuesService.java:343-367                                           │
│    - Uses regex EXPRESSION_PATTERN: #\{([a-z0-9A-Z_.]+)\s?((?:,?'([^']+)')*)│
│    - Finds each #{...} directive in the expression                          │
│    - For "#{first_name}": resolves to Name.firstName() method               │
└────────────────────────────────────┬────────────────────────────────────────┘
                                     ▼
┌─────────────────────────────────────────────────────────────────────────────┐
│ 6. Resolving #{first_name} directive                                        │
│    FakeValuesService.java:380-421                                           │
│    Search order:                                                            │
│    a) Try method on current object (Name) → Name.firstName() ✓              │
│    b) If not found: try YAML key "name.first_name"                          │
│    c) If not found: try method on Faker root object                         │
│    d) If not found: try lowercase YAML lookup                               │
└────────────────────────────────────┬────────────────────────────────────────┘
                                     ▼
┌─────────────────────────────────────────────────────────────────────────────┐
│ 7. Name.firstName() is invoked via reflection                               │
│    FakeValuesService.java:468-481 (resolveFromMethodOn)                     │
│    - Uses accessor() to find method ignoring case                           │
│    - Invokes method.invoke(obj, args)                                       │
└────────────────────────────────────┬────────────────────────────────────────┘
                                     ▼
┌─────────────────────────────────────────────────────────────────────────────┐
│ 8. Name.firstName()                                                         │
│    Name.java:61-63                                                          │
│    Calls: faker.fakeValuesService().resolve("name.first_name", this, faker) │
└────────────────────────────────────┬────────────────────────────────────────┘
                                     ▼
┌─────────────────────────────────────────────────────────────────────────────┐
│ 9. safeFetch("name.first_name")                                             │
│    FakeValuesService.java:159-173                                           │
│    - Gets list from YAML: ["#{female_first_name}", "#{male_first_name}"]    │
│    - Randomly selects one → e.g., "#{male_first_name}"                      │
└────────────────────────────────────┬────────────────────────────────────────┘
                                     ▼
┌─────────────────────────────────────────────────────────────────────────────┐
│ 10. resolveExpression("#{male_first_name}", ...)                            │
│     - Looks up "name.male_first_name" in YAML                               │
│     - Gets array of 1000+ male names                                        │
│     - RandomService.nextInt() picks one → e.g., "James"                     │
└────────────────────────────────────┬────────────────────────────────────────┘
                                     ▼
┌─────────────────────────────────────────────────────────────────────────────┐
│ 11. Same process for #{last_name}                                           │
│     - Name.lastName() → resolve("name.last_name")                           │
│     - safeFetch gets array of 400+ last names                               │
│     - RandomService picks one → e.g., "Smith"                               │
└────────────────────────────────────┬────────────────────────────────────────┘
                                     ▼
┌─────────────────────────────────────────────────────────────────────────────┐
│ 12. Final result: "James Smith"                                             │
│     Expression "#{first_name} #{last_name}" becomes "James Smith"           │
└─────────────────────────────────────────────────────────────────────────────┘
```

## Key Components Summary

| Step | Class | Method | Purpose |
|------|-------|--------|---------|
| 1 | `Faker` | `name()` | Returns cached `Name` instance |
| 2-3 | `Name` | `fullName()` → `name()` | Delegates to resolve |
| 4-6 | `FakeValuesService` | `resolve()` → `resolveExpression()` | Parses `#{...}` expressions |
| 7 | `FakeValuesService` | `resolveFromMethodOn()` | Reflection-based method invocation |
| 8-11 | `FakeValuesService` | `safeFetch()` → `fetchObject()` | YAML data lookup + random selection |

## Data Flow

```
en/name.yml                          Code
─────────────────────────────────    ──────────────────────────
name:
  name:                              ← resolve("name.name")
    - "#{first_name} #{last_name}"

  first_name:                        ← resolve("name.first_name")
    - "#{male_first_name}"
    - "#{female_first_name}"

  male_first_name: [James, ...]      ← fetch("name.male_first_name")
  last_name: [Smith, ...]            ← fetch("name.last_name")
```

## Key Insight

The `FakeValuesService` recursively resolves `#{...}` expressions, which can reference either:

1. **Methods on the current object** (like `firstName()` on `Name`)
2. **Other YAML keys** (like `male_first_name`)
3. **Methods on `Faker` object** (like `regexify()`)
