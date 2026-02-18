# Pre-commit Hooks Setup Plan for java-faker

## Overview
Set up Lefthook (Go-based, no Python dependency) with pre-commit hooks that integrate with the existing `justfile` workflow.

## Components to Add

### 1. Lefthook Configuration
**File:** `lefthook.yml` (project root)

```yaml
pre-commit:
  parallel: true
  commands:
    compile:
      run: just build

    format-check:
      glob: "*.java"
      run: just format-check

    yaml-validate:
      glob: "*.{yml,yaml}"
      run: just yaml-check

commit-msg:
  commands:
    conventional:
      run: just commit-msg-check {1}
```

### 2. Code Formatting (Google Java Format via Spotless)
**File:** `pom.xml` - Add Spotless Maven Plugin

Spotless will:
- Format Java code using Google Java Format style
- Run as `mvn spotless:check` (verify) or `mvn spotless:apply` (fix)

### 3. Justfile Updates
**File:** `justfile` - Add new recipes

| Recipe | Command | Purpose |
|--------|---------|---------|
| `format` | `mvn spotless:apply` | Auto-format all Java files |
| `format-check` | `mvn spotless:check` | Verify formatting (for CI/hooks) |
| `yaml-check` | Validate YAML files | Check locale files syntax |
| `commit-msg-check` | Validate commit message | Enforce conventional commits (optional) |
| `hooks-install` | `lefthook install` | Set up git hooks |

### 4. Documentation
**File:** `README.md` or `CONTRIBUTING.md` - Add setup instructions

---

## Files to Modify

| File | Action |
|------|--------|
| `lefthook.yml` | Create new |
| `pom.xml` | Add Spotless plugin |
| `justfile` | Add format/hook recipes |

---

## Installation Steps (for contributors)

1. Install Lefthook: `brew install lefthook` (or download binary)
2. Install hooks: `just hooks-install`
3. (Optional) Format existing code: `just format`

---

## What Each Hook Does

### Pre-commit Hooks
1. **compile** - Runs `mvn compile` to catch syntax errors
2. **format-check** - Verifies Java files are formatted correctly (fails if not)
3. **yaml-validate** - Validates YAML syntax in locale files

### Commit-msg Hook (Optional)
- Enforces conventional commit format (e.g., `feat:`, `fix:`, `docs:`)

---

## Verification

After implementation:
1. Run `just hooks-install` to install hooks
2. Make a test commit with unformatted code → should fail
3. Run `just format` then commit again → should pass
4. Run `just format-check` in CI to ensure consistency

---

## Trade-offs Considered

| Decision | Choice | Rationale |
|----------|--------|-----------|
| Hook framework | Lefthook | No Python dependency, single Go binary |
| Formatter | Spotless + Google Java Format | Maven-native, widely adopted style |
| YAML validation | Simple syntax check | Sufficient for locale files |
| Test execution | Not in pre-commit | Too slow; rely on CI |

---

## Optional Extensions (Not Included)

- Checkstyle for additional static analysis
- License header checks
- Commit message linting (can add if desired)
