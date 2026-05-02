@echo off
REM Initialize git, commit, create GitHub repo and push using GitHub CLI (gh)
REM Run this from the repository root (Windows cmd.exe)

:: Ensure .gitignore is present before adding files
@if not exist .gitignore (
  echo WARNING: .gitignore not found. Create or verify .gitignore before proceeding.
)
git init
git add .
git commit -m "Initial commit: AI Android Calculator"
echo Creating GitHub repository using gh CLI...
gh repo create "AI-Android-Calculator" --public --source=. --remote=origin --push
echo Done. If gh prompted for login, follow instructions to authenticate.
echo Repository should now be pushed to GitHub.
pause
