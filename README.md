AI Android Calculator
=====================

Overview
--------
AI Android Calculator is an advanced, modern Android calculator app that goes beyond mere number crunching. It integrates an intelligent AI Chatbot and Math Tutor, real-time currency conversion, and a robust scientific calculator into a single, clean user interface. The project is highly tested and accessible, providing a premier calculator experience on Android.

Key Features
------------
- **Basic Arithmetic**: Addition, subtraction, multiplication, and division.
- **Scientific Calculator**: Sine, cosine, tangent, logarithms (log, ln), exponents, square roots, and factorials.
- **AI Math Tutor & Chatbot**: Integrated with the Anthropic Claude API to help users understand complex math problems step-by-step.
- **Currency Converter**: Real-time currency conversion using external exchange rate APIs.
- **Expression Parsing**: Correct operator precedence and nested parentheses evaluation.
- **Memory & History**: Calculation history tracking and memory functions (M+, M-, MR, MC).
- **Modern UI/UX**: Configurable themes (light/dark mode) and smooth animations.
- **Accessibility Integration**: Enhanced with TalkBack labels and rich content descriptions.
- **Testing Integration**: Comprehensive unit tests and Android instrumentation tests (Cucumber feature files included).

Repository Contents
-------------------
- `app/` - Android app module including source code, resources, API clients, and tests.
- `build.gradle.kts`, `settings.gradle.kts` - Gradle Kotlin DSL build files.
- `gradle/` - Gradle wrapper and configuration.
- Markdown documentation and test reports.

Build & Run (Android Studio)
----------------------------
1. Open Android Studio.
2. Choose "Open an existing project" and select this repository directory.
3. Configure your API keys (e.g., Anthropic API key) inside the app or via `local.properties` to fully enable the AI features.
4. Let Gradle sync and install any SDK components if prompted.
5. Run the app on an emulator or a physical device.

Build & Run (Command Line - Windows cmd.exe/PowerShell)
-------------------------------------------------------
From the repository root, build the app using Gradle:

    gradlew assembleDebug
    gradlew installDebug

*Note: Use `gradlew.bat` on Windows. This will output an APK to your `/outputs` directory.*

Run Unit Tests:

    gradlew test

Run Connected Android Instrumentation Tests:

    gradlew connectedAndroidTest

Contributing
------------
Contributions are welcome! Please open an issue or pull request. Ensure that you write tests for new features and verify all integrations before submitting.

License
-------
This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.
