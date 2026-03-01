---
name: implement-spec
description: Implements a spec end-to-end from a spec file. Creates a GitHub issue, a kebab-case feature branch, writes all required files, commits, and opens a PR via create-pr.sh. Works for new faker providers as well as general workflow or tooling specs. Invoke manually with the path to a spec file.
argument-hint: <path/to/SPEC.md>
disable-model-invocation: true
allowed-tools: Bash, Read, Write, Edit, Glob, Grep
---

Implement the feature described in the spec file below.

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
- The feature name (e.g., "CreditCard", "Subscription", "PreCommitHooks")
- All files to create or modify (from the spec's **File Locations** table or equivalent section)
- The complete list of public methods / deliverables and their expected behavior
- The verification checklist at the bottom of the spec

**Classify the spec** — decide which category it falls into:
- **New faker**: the primary deliverable is a new Java faker provider (YAML data file, Java class, Faker.java registration, test class).
- **General**: anything else (workflow tooling, build config, documentation changes, etc.).

Record this classification in your working memory; it drives conditional guidance in Step 4.

### Step 1b — Read LEARNINGS.md

Before touching any code, read `LEARNINGS.md` at the repository root:

```bash
cat LEARNINGS.md
```

Apply every relevant lesson during implementation in Step 4. These are hard-won lessons from previous implementations — ignoring them causes the same mistakes to recur.

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

Derive a descriptive kebab-case branch name from the spec's content:

- **New faker spec**: `add-<feature-name>-faker` (e.g., `add-credit-card-faker`, `add-markdown-faker`)
- **General spec**: a name that reflects the nature of the change (e.g., `add-pre-commit-hooks`, `update-build-config`, `fix-yaml-quoting`)

```bash
git checkout master
git pull
git checkout -b <derived-branch-name>
```

### Step 4 — Implement the spec

> **Apply every lesson from LEARNINGS.md as you implement.** Do not skip lessons that are relevant to the files you are creating or modifying.

Create or modify every file listed in the spec's **File Locations** table. Implement exactly what the spec describes — no more, no less.

---

#### If the spec is a **new faker**:

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

---

#### If the spec is **general** (non-faker):

Follow the file list from the spec's **File Locations** table (or equivalent section) exactly. Do not apply faker-specific assumptions. Implement each file as the spec describes, then verify using whatever verification steps the spec prescribes (e.g., running a script, checking tool output, reviewing generated config).

### Step 5 — Reflect and update LEARNINGS.md

Before committing, pause and reflect on what happened during this implementation session.

Ask yourself:
- Did I make any mistakes that cost time to debug?
- Was any information in the spec contradictory or ambiguous?
- Were there non-obvious gotchas in the codebase (naming conventions, YAML quirks, test patterns)?
- Was there missing context that would have saved effort if documented upfront?

For each genuine finding, add a concise numbered entry to `LEARNINGS.md` at the repository root, following the existing format:

```
## <N>. <Short heading>

**Problem**: <what went wrong or was unclear>

**Solution/Rule**: <the rule or fix to apply next time>
```

Only add entries for lessons that are not already documented. Skip anything already covered.

### Step 6 — Commit

Stage only the files you created or modified. Commit with a message that references the issue number:

```bash
git add src/
git commit -m "add <FeatureName> faker (closes #<issue-number>)"
```

### Step 7 — Push and open PR

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
