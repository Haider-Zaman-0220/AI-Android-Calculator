AI Android Calculator
=====================

Overview
--------
AI Android Calculator is an Android calculator app project (source included) that provides a modern, accessible, and well-tested calculator experience. It contains both basic and scientific calculator features, a clean UI, unit tests, and instrumentation tests. This repository is organized for easy development in Android Studio or via Gradle.

Key features
------------
- Basic arithmetic: addition, subtraction, multiplication, division
- Scientific functions: sin, cos, tan, log, ln, exponent, square root, factorial
- Expression parsing with correct operator precedence and parentheses
- Calculation history and memory functions (M+, M-, MR, MC)
- Configurable themes (light/dark)
- Accessibility improvements (TalkBack labels, content descriptions)
- Localization-ready strings
- Unit tests and Android instrumentation/UI tests (Cucumber feature files included)

Repository contents
-------------------
- `app/` - Android app module with source code, resources, and tests
- `build.gradle.kts`, `settings.gradle.kts` - Gradle Kotlin DSL build files
- `gradle/` - Gradle wrapper and configuration
- Documentation and test-related markdown files

Sensitive files & privacy
-------------------------
This project intentionally does not commit sensitive or machine-specific files. The `.gitignore` in this repository already excludes:
- `local.properties` (SDK path and local settings)
- Keystore files (`*.jks`, `*.keystore`)
- Build artifacts (`/build`, `/app/build`, `.gradle`)
- Log files (`hs_err_pid*.log`, `replay_pid*.log`)

If you have already committed secrets (keystores, API keys, or `local.properties`), follow the "If sensitive files were already committed" section below to remove them from the repository history before pushing.

Build & run (Android Studio)
---------------------------
1. Open Android Studio.
2. Choose "Open an existing project" and select this repository directory.
3. Let Gradle sync and install any SDK components if prompted.
4. Run the app on an emulator or device.

Build & run (command line - Windows cmd.exe)
-------------------------------------------
In a Windows `cmd.exe` shell, from the repository root run:

    gradlew assembleDebug
    gradlew installDebug

Note: `gradlew` is the Gradle wrapper script included in the repo. Use `gradlew.bat` on Windows if needed.

Run unit tests:

    gradlew test

Run connected Android instrumentation tests:

    gradlew connectedAndroidTest

Create and push a GitHub repository
----------------------------------
Below are two common ways to create a remote GitHub repository and push this project. Choose the one that matches tools you have installed.

1) Using GitHub CLI (`gh`) - preferred if installed:

    REM from Windows cmd.exe
    git init
    git add .
    git commit -m "Initial commit: AI Android Calculator"
    gh repo create AI-Android-Calculator --public --source=. --remote=origin --push

This will create a GitHub repo named `AI-Android-Calculator`, add `origin` remote and push your current branch.

2) Manually via GitHub website:

    REM from Windows cmd.exe
    git init
    git add .
    git commit -m "Initial commit: AI Android Calculator"
    REM Create a new repo on GitHub (visit https://github.com/new) and name it "AI-Android-Calculator"
    REM After creating the repo, you'll be given commands similar to:
    git remote add origin https://github.com/<your-username>/AI-Android-Calculator.git
    git branch -M main
    git push -u origin main

Important: ensure `.gitignore` is in place before `git add` so local/sensitive files are not staged.

If sensitive files were already committed
---------------------------------------
If you accidentally committed files you want to remove (for example `local.properties`, `*.jks`, or log files), do NOT push. Instead:

1) Remove the file and unstage it:

    git rm --cached local.properties
    git commit -m "Remove local.properties from repository"

2) To purge a file from history (recommended if it contained secrets), use `git filter-repo` or the BFG Repo-Cleaner. Example with `git filter-repo`:

    REM Install git-filter-repo (see https://github.com/newren/git-filter-repo)
    git filter-repo --path local.properties --invert-paths

For BFG (alternate cleanup tool):

    java -jar bfg.jar --delete-files local.properties
    git reflog expire --expire=now --all && git gc --prune=now --aggressive

After history-rewrite, force-push to remote:

    git push --force --all
    git push --force --tags

Be careful: rewriting history affects collaborators. Coordinate if working with others.

Contributing
------------
Contributions are welcome. Please open issues or pull requests. Keep changes focused and include tests for bug fixes or new features.

License
-------
Choose a license suitable for your project. This repository does not include a license file by default. Add one (for example, `LICENSE` with the MIT license) if you want to make the project open source.

Support
-------
If you want, I can also initialize the Git repo locally and attempt to push to GitHub for you — but I'll need either a working `gh` CLI on your machine or a personal access token configured. Tell me how you'd like to proceed.
