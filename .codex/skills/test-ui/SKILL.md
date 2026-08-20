---
name: test-ui
description: Run Labubu console UI tests from test/ui-test-plan.md, compare each command sequence with its expected output, and save a console transcript. Use when asked to test or verify interactive command-line behaviour.
---

# Test UI

Use `test/ui-test-plan.md` as the source of truth for each test's aim, console inputs, and exact expected console output.

## Test-plan format

Add a `## Test case: <name>` section with one `**Aim:**` line and `text` code blocks for `**Inputs:**` and `**Expected output:**`. Inputs are entered one per line and should end with `bye`.

## Run

From the repository root, run:

```powershell
py .codex/skills/test-ui/scripts/run-ui-tests.py
```

The runner compiles `src/main/java/*.java` using JDK 25, compares output exactly (apart from line-ending and trailing-newline differences), and records each completed test in `_temp/ui-test-session.md`. Stop at the first failure and report the aim plus expected and actual output.
