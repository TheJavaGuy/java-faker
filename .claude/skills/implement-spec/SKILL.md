---
name: implement-spec
description: Implements a new Java faker feature end-to-end from a spec file. Creates a GitHub issue, a kebab-case feature branch, writes all Java and YAML files, commits, and opens a PR via create-pr.sh. Invoke manually with the path to a spec file.
argument-hint: <path/to/SPEC.md>
disable-model-invocation: true
allowed-tools: Bash, Read, Write, Edit, Glob, Grep
---

Implement the Java faker feature described in the spec file below.

---

## Spec file: $ARGUMENTS

```
!`cat "$ARGUMENTS"`
```

---

## Instructions

Follow every step in order. Do not skip any step.

### Step 1 — Understand the spec

Read the spec above carefully. Identify:
- The feature name (e.g., "CreditCard", "Subscription")
- All files to create or modify (YAML, Java class, Faker.java, test class)
- The complete list of public methods and their expected behavior
- The verification checklist at the bottom of the spec

### Step 2 — Create a GitHub issue

Create an issue in the `TheJavaGuy/java-faker` repository. Derive the title from the spec's **Overview** section (one concise sentence). Use the full spec content as the body.

```bash
gh issue create \
  --repo TheJavaGuy/java-faker \
  --title "<feature title from spec Overview>" \
  --body "$(cat '$ARGUMENTS')"
```

Record the issue number printed (e.g., `#42`). You will reference it in the commit message.

### Step 3 — Create a feature branch

Derive a descriptive kebab-case branch name from the feature name. Pattern:

```
add-<feature-name>-faker
```

Examples: `add-credit-card-faker`, `add-markdown-faker`, `add-shipping-faker`.

```bash
git checkout master
git pull
git checkout -b add-<feature-name>-faker
```

### Step 4 — Implement the spec

Create or modify every file listed in the spec's **File Locations** table. Implement exactly what the spec describes — no more, no less.

**Typical files for a new faker:**

1. **YAML data file** — `src/main/resources/en/<feature>.yml`
   - Match the structure shown in the spec
   - Follow the YAML conventions of existing files (e.g., `vehicle.yml`, `finance.yml`)

2. **Java class** — `src/main/java/org/thejavaguy/javafaker/<FeatureName>.java`
   - Use `package org.thejavaguy.javafaker;`
   - Follow the pattern of existing fakers: constructor takes `Faker faker`, methods delegate to `faker.fakeValuesService()` or other fakers
   - Implement all methods listed in the spec

3. **Faker.java registration** — `src/main/java/org/thejavaguy/javafaker/Faker.java`
   - Add a `private final <FeatureName> <fieldName>;` field near similar fields
   - Initialize it in the constructor: `this.<fieldName> = new <FeatureName>(this);`
   - Add a getter method: `public <FeatureName> <fieldName>() { return <fieldName>; }`

4. **Test class** — `src/test/java/org/thejavaguy/javafaker/<FeatureName>Test.java`
   - Extend `AbstractFakerTest`
   - Implement all test cases from the spec
   - Use AssertJ assertions (`assertThat(...)`)

Before moving on, run tests and fix any failures:

```bash
./mvnw test -Dtest=<FeatureName>Test
```

If all tests pass, also run the full suite to check for regressions:

```bash
./mvnw test
```

### Step 5 — Commit

Stage only the files you created or modified. Commit with a message that references the issue number:

```bash
git add src/
git commit -m "add <FeatureName> faker (closes #<issue-number>)"
```

### Step 6 — Push and open PR

Push the branch and create the PR using the existing helper script:

```bash
git push -u origin HEAD
./create-pr.sh
```

`create-pr.sh` automatically derives the PR title from the branch name and builds the body from the commit log. The PR will target `master`.

### Done

Report back with:
- The GitHub issue URL
- The branch name
- The PR URL
- A summary of all files created/modified
