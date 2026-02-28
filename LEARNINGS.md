# Development Learnings

This document captures important lessons learned during development to prevent future mistakes.

## Adding New Faker Providers

### 1. YAML files MUST be registered in `EnFile.java`

**Problem**: Creating `src/main/resources/en/*.yml` is NOT enough. Without registration, `fetchString()` calls return `null` even if the file exists.

**Solution**:
- Add filename to `FILES` list in `src/main/java/org/thejavaguy/javafaker/service/files/EnFile.java`
- Example: `"credit_card.yml",` must be added to the list

**Root cause**: The `FakeValuesService` loads YAML files through `EnFile.getFiles()` - new files are invisible until registered there.

### 2. Match existing YAML array format exactly

**Problem**: Using dash-based YAML list format instead of project's square bracket notation.

**Wrong**:
```yaml
banks:
  - "Chase"
  - "Wells Fargo"
```

**Correct**:
```yaml
banks: ["Chase", "Wells Fargo"]
```

**Solution**: Always check similar files (gender.yml, demographic.yml, bank.yml) for conventions before creating new YAML files.

### 3. Test incrementally, not all at once

**Problem**: Creating all files (YAML, Java class, tests) before running any tests makes debugging harder.

**Better approach**:
1. Create YAML file
2. Register in EnFile.java
3. Run a simple test to verify YAML loads
4. Then create Java class and full test suite

**Benefit**: Catches configuration issues before writing hundreds of lines of code.

### 4. YAML numeric values cause `ClassCastException` in `fetchString()`

**Problem**: Unquoted single-digit numbers in YAML are parsed as integers, not strings. Calling `fetchString()` on them throws `ClassCastException: Integer cannot be cast to String` at runtime.

**Wrong**:
```yaml
doors: [1, 2, 3, 4]
```

**Correct**:
```yaml
doors: ['1', '2', '3', '4']
```

**Rule**: Always quote values in YAML lists that will be accessed via `fetchString()`, even if they look numeric.

### 5. Use `bothify(pattern, true)` for uppercase license plates / codes

**Problem**: `bothify(pattern)` substitutes `?` placeholders with **lowercase** letters, producing values like `"g99-seb5"` instead of `"G99-SEB5"`.

**Solution**: Pass `true` as the second argument to get uppercase output:
```java
// Wrong — generates lowercase letters
return faker.bothify(pattern);

// Correct — generates uppercase letters
return faker.bothify(pattern, true);
```

**When to use**: Any time the generated string should be uppercase (license plates, tracking numbers, codes). See `Shipping.trackingNumberUps()` for the established pattern in this codebase.

---

*Last updated: 2026-02-28*
