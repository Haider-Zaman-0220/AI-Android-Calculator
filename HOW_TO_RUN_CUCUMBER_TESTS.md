# How to Run Cucumber Tests in Android

## Why Tests Don't Run from "Run" Button

Unlike traditional JUnit tests, Cucumber tests on Android **cannot** be run by right-clicking on the test class and selecting "Run". This is because:

1. Android Cucumber tests must run as **instrumented tests** on a device/emulator
2. They require the special `CucumberTestRunner` configured in `build.gradle.kts`
3. The `@RunWith(Cucumber::class)` annotation causes Java 8 time API errors on Android

## How to Run Cucumber Tests

### Method 1: Command Line (Recommended)

1. **Connect a device or start an emulator**

2. **Run all tests:**
   ```bash
   ./gradlew connectedAndroidTest
   ```

3. **View results:**
   - HTML report: `app/build/reports/androidTests/connected/index.html`
   - XML report: `app/build/outputs/androidTest-results/connected/`

### Method 2: Android Studio Configuration

1. **Click on "Run" dropdown** (top toolbar, next to the green play button)

2. **Click "Edit Configurations..."**

3. **Click the "+" button** and select **"Android Instrumented Tests"**

4. **Configure:**
   - Name: `Cucumber Tests`
   - Module: `app`
   - Test: `All in Package`
   - Package: `com.example.mycalculatorapp`
   - Instrumentation runner: `com.example.mycalculatorapp.CucumberTestRunner`

5. **Click "OK"**

6. **Connect device/emulator**

7. **Select "Cucumber Tests"** from the dropdown and click the green play button

### Method 3: Run from Gradle Panel

1. **Open Gradle panel** (right side of Android Studio)

2. **Navigate to:**
   ```
   Android-Calculator-App
   └── app
       └── Tasks
           └── verification
               └── connectedAndroidTest
   ```

3. **Double-click** `connectedAndroidTest`

## Running Specific Test Scenarios

### Run a specific feature file:
```bash
./gradlew connectedAndroidTest -Pandroid.testInstrumentationRunnerArguments.features=features/basic_arithmetic.feature
```

### Run a specific scenario by name:
```bash
./gradlew connectedAndroidTest -Pandroid.testInstrumentationRunnerArguments.name="Add two numbers"
```

### Run with tags (if you add tags to scenarios):
```bash
./gradlew connectedAndroidTest -Pandroid.testInstrumentationRunnerArguments.tags="@smoke"
```

## Troubleshooting

### "No tests found"
- Make sure device/emulator is connected and running
- Check that feature files are in `app/src/androidTest/assets/features/`
- Verify step definitions are in `app/src/androidTest/java/com/example/mycalculatorapp/`

### "java.time.Clock" error
- This happens if you try to use `@RunWith(Cucumber::class)` on a test class
- Don't use that annotation - use the `CucumberTestRunner` instead via gradle

### Tests fail to start
- Clean and rebuild:
  ```bash
  ./gradlew clean connectedAndroidTest
  ```

### App not installed on device
- Uninstall the app manually from device
- Run:
  ```bash
  ./gradlew uninstallAll
  ./gradlew connectedAndroidTest
  ```

## Available Feature Files

1. **basic_arithmetic.feature** - Basic calculator operations (+, -, ×, ÷)
2. **scientific_calculator.feature** - Scientific functions (sin, cos, tan, sqrt, etc.)
3. **currency_converter.feature** - Currency conversion
4. **chatbot.feature** - AI chatbot interactions
5. **math_tutor.feature** - Math tutor bubble and explanations
6. **feature_integration.feature** - Cross-feature navigation and integration

## Test Reports

After running tests, you can find detailed reports at:

- **HTML Report (Open in browser):**
  ```
  app/build/reports/androidTests/connected/index.html
  ```

- **Cucumber Reports (if working):**
  ```
  /data/data/com.example.mycalculatorapp/cucumber-reports/cucumber.html
  ```

- **XML Results:**
  ```
  app/build/outputs/androidTest-results/connected/
  ```

## Quick Reference

| Action | Command |
|--------|---------|
| Run all tests | `./gradlew connectedAndroidTest` |
| Clean and test | `./gradlew clean connectedAndroidTest` |
| Uninstall app | `./gradlew uninstallAll` |
| View reports | Open `app/build/reports/androidTests/connected/index.html` |
| Check connected devices | `adb devices` |

## Important Notes

1. **Always have device/emulator running** before running tests
2. **Tests take time** - be patient, especially on first run
3. **Network required** for AI features (chatbot, math tutor)
4. **Tests modify app state** - they will navigate through your app
5. **Don't interact with device** while tests are running

## Viewing Test Execution

You can watch test execution by:

1. **Looking at the device screen** - tests will navigate through your app
2. **Logcat in Android Studio** - shows detailed logs
3. **Run panel** - shows test progress and results

