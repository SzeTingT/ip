#!/usr/bin/env python3
"""Compile Labubu and run the console UI test plan."""
from __future__ import annotations

import os
import re
import shutil
import subprocess
import sys
from pathlib import Path


def normalise(text: str) -> str:
    """Return text with platform-independent line endings."""
    return text.replace("\r\n", "\n").replace("\r", "\n").rstrip("\n")


def field(section: str, name: str) -> str:
    """Read a required text code block from one Markdown test case."""
    match = re.search(rf"^\*\*{name}:\*\*\s*\n```text\n(.*?)\n```", section, re.M | re.S)
    if match is None:
        raise ValueError(f"Missing {name} block.")
    return match.group(1)


def load_cases(plan: Path) -> list[tuple[str, str, str, str]]:
    """Load test names, aims, inputs, and expected output from the plan."""
    content = plan.read_text(encoding="utf-8")
    headings = list(re.finditer(r"^## Test case: (.+)$", content, re.M))
    if not headings:
        raise ValueError("The test plan has no test cases.")
    cases = []
    for index, heading in enumerate(headings):
        end = headings[index + 1].start() if index + 1 < len(headings) else len(content)
        section = content[heading.end():end]
        aim = re.search(r"^\*\*Aim:\*\*\s*(.+)$", section, re.M)
        if aim is None:
            raise ValueError(f"Test case '{heading.group(1)}' is missing an aim.")
        cases.append((heading.group(1).strip(), aim.group(1).strip(),
                      field(section, "Inputs"), field(section, "Expected output")))
    return cases


def java_tool(name: str) -> str:
    """Find a JDK tool, preferring JAVA_HOME."""
    suffix = ".exe" if os.name == "nt" else ""
    if os.environ.get("JAVA_HOME"):
        candidate = Path(os.environ["JAVA_HOME"]) / "bin" / f"{name}{suffix}"
        if candidate.is_file():
            return str(candidate)
    found = shutil.which(name)
    if found:
        return found
    raise FileNotFoundError(f"Cannot find {name}; set JAVA_HOME to JDK 25.")


def write_session(path: Path, records: list[tuple[str, str, str, str, bool]]) -> None:
    """Write the console input and observed output for completed test cases."""
    lines = ["# UI Test Session", ""]
    for name, aim, inputs, actual, passed in records:
        lines += [f"## {name} — {'PASSED' if passed else 'FAILED'}", "", f"**Aim:** {aim}", "",
                  "**Console input:**", "```text", inputs, "```", "", "**Console output:**", "```text",
                  actual, "```", ""]
    path.write_text("\n".join(lines), encoding="utf-8")


def main() -> int:
    """Compile the project and stop at the first failed console test."""
    if hasattr(sys.stdout, "reconfigure"):
        sys.stdout.reconfigure(encoding="utf-8", errors="backslashreplace")
    root = Path.cwd()
    try:
        cases = load_cases(root / "test" / "ui-test-plan.md")
        javac, java = java_tool("javac"), java_tool("java")
    except (FileNotFoundError, ValueError) as error:
        print(f"Test setup failed: {error}")
        return 2
    classes = root / "_temp" / "ui-test-classes"
    session = root / "_temp" / "ui-test-session.md"
    classes.mkdir(parents=True, exist_ok=True)
    session.parent.mkdir(parents=True, exist_ok=True)
    sources = sorted((root / "src" / "main" / "java").glob("*.java"))
    compile_result = subprocess.run([javac, "-encoding", "UTF-8", "-d", str(classes), *map(str, sources)],
                                    cwd=root, capture_output=True, text=True, encoding="utf-8", errors="replace")
    if compile_result.returncode:
        print("Compilation failed:\n" + compile_result.stderr)
        return 2
    records = []
    for name, aim, inputs, expected in cases:
        result = subprocess.run([java, "-Dfile.encoding=UTF-8", "-Dstdout.encoding=UTF-8", "-Dstderr.encoding=UTF-8",
                                 "-cp", str(classes), "Labubu"], cwd=root, input=inputs + "\n", capture_output=True,
                                text=True, encoding="utf-8", errors="replace")
        actual = normalise(result.stdout + result.stderr)
        passed = result.returncode == 0 and actual == normalise(expected)
        records.append((name, aim, inputs, actual, passed))
        write_session(session, records)
        if not passed:
            print(f"FAILED: {name}\nAim: {aim}\nExpected output:\n{normalise(expected)}\nActual output:\n{actual}")
            print(f"Session record: {session}")
            return 1
        print(f"PASSED: {name}")
    print(f"All {len(cases)} test case(s) passed.\nSession record: {session}")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
