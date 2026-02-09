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

---

*Last updated: 2026-02-09*
