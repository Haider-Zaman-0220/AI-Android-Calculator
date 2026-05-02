# How to Run Your BDD Tests

## Problem Summary
Previously, when you clicked "All tests", only the `ExampleInstrumentedTest` was running instead of your Cucumber BDD tests.

## What Was Fixed

### 1. Added @RunWith Annotation
The `RunCucumberTest.kt` file was missing the `@RunWith(Cucumber::class)` annotation, which tells JUnit to use the Cucumber test runner.

**Before:**
```kotlin
@CucumberOptions(...)
class RunCucumberTest {}
```

**After:**
```kotlin
@RunWith(Cucumber::class)
@CucumberOptions(...)
class RunCucumberTest
```

### 2. Created Custom Test Runner
Created `CucumberTestRunner.kt` that extends `CucumberAndroidJUnitRunner` to properly run Cucumber tests on Android.

### 3. Updated build.gradle.kts
Changed the test instrumentation runner from the default to our custom Cucumber runner:

**Before:**
```kotlin
testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
```

**After:**
```kotlin
testInstrumentationRunner = "com.example.mycalculatorapp.CucumberTestRunner"
```

## How to Run Your Tests

### Option 1: Run All BDD Tests (Recommended)

1. **In Android Studio:**
   - Open the Project view
   - Navigate to: `app/src/androidTest/java/com.example.mycalculatorapp/test/`
   - Right-click on `RunCucumberTest.kt`
   - Select **"Run 'RunCucumberTest'"**

   This will run ALL your feature files:
   - ✅ basic_arithmetic.feature
   - ✅ chatbot.feature
   - ✅ currency_converter.feature
   - ✅ feature_integration.feature
   - ✅ math_tutor.feature
   - ✅ scientific_calculator.feature

2. **From Command Line:**
   ```powershell
   .\gradlew connectedAndroidTest
   ```

### Option 2: Run Individual Feature Files

1. **In Android Studio:**
   - Navigate to: `app/src/androidTest/assets/features/`
   - Right-click on any `.feature` file (e.g., `basic_arithmetic.feature`)
   - Select **"Run 'Feature: Basic Arithmetic'"**

2. **From Command Line (specific feature):**
   ```powershell
   .\gradlew connectedAndroidTest -Pandroid.testInstrumentationRunnerArguments.tags=@basic_arithmetic
   ```
   *(Note: You need to add tags to your feature files for this to work)*

### Option 3: Run Individual Scenarios

1. **In Android Studio:**
   - Open a `.feature` file
   - Click the green play button (▶) next to any `Scenario:` line
   - This will run only that specific scenario

### Option 4: Run from Android Studio Test Panel

1. **Sync Gradle** first:
   - Click **File → Sync Project with Gradle Files**
   - Wait for sync to complete

2. **Open Test Panel:**
   - Click **View → Tool Windows → Run**
   - Or press **Alt+4**

3. **Run Tests:**
   - You should now see `RunCucumberTest` in the test tree
   - Right-click it and select **"Run 'RunCucumberTest'"**

## Test Structure

Your BDD test suite is organized as follows:

```
app/src/androidTest/
├── assets/
│   └── features/                         # Feature files (Gherkin)
│       ├── basic_arithmetic.feature      # Basic calc operations
│       ├── chatbot.feature               # AI chatbot tests
│       ├── currency_converter.feature    # Currency conversion
│       ├── feature_integration.feature   # Cross-feature tests
│       ├── math_tutor.feature            # Math tutor AI tests
│       └── scientific_calculator.feature # Scientific calc tests
│
└── java/com/example/mycalculatorapp/
    ├── test/
    │   └── RunCucumberTest.kt            # Main test runner
    │
    ├── CucumberTestRunner.kt             # Custom runner implementation
    │
    └── Step Definitions:                 # Step implementations
        ├── CalculatorSteps.kt
        ├── ChatbotSteps.kt
        ├── CurrencyConverterSteps.kt
        ├── FeatureIntegrationSteps.kt
        ├── MathTutorSteps.kt
        └── ScientificCalculatorSteps.kt
```

## Troubleshooting

### Issue: "All tests" still only runs ExampleInstrumentedTest

**Solution:**
1. **Sync Gradle:**
   ```
   File → Sync Project with Gradle Files
   ```

2. **Invalidate Caches:**
   ```
   File → Invalidate Caches → Invalidate and Restart
   ```

3. **Rebuild Project:**
   ```powershell
   .\gradlew clean
   .\gradlew assembleDebugAndroidTest
   ```

### Issue: Tests not found or "No tests were found"

**Solution:**
1. Make sure device/emulator is connected
2. Verify the feature files are in `androidTest/assets/features/`
3. Check that all step definitions are in the correct package

### Issue: "Step definition not found"

**Solution:**
1. Verify all step definition files are in `com.example.mycalculatorapp` package
2. Check that the glue path in `RunCucumberTest.kt` is correct:
   ```kotlin
   glue = ["com.example.mycalculatorapp"]
   ```

### Issue: Tests fail with resource not found errors

**Solution:**
1. Check that all resource IDs match the actual layout files
2. Verify the layout files exist in `res/layout/`
3. Run a clean build:
   ```powershell
   .\gradlew clean assembleDebug
   ```

## Viewing Test Results

### In Android Studio:
- Results appear in the **Run** panel at the bottom
- Green ✅ = Passed
- Red ❌ = Failed
- Click on any test to see details

### In HTML Report:
```powershell
.\gradlew connectedAndroidTest
```
Then open: `app/build/reports/androidTests/connected/index.html`

### Command Line Output:
Look for:
```
> Task :app:connectedDebugAndroidTest
Successfully installed app...

Feature: Basic Arithmetic
  Scenario: Add two numbers               PASSED
  Scenario: Subtract two numbers          PASSED
  ...
```

## Running Tests Before Committing

**Best Practice:** Always run tests before pushing code

```powershell
# Quick test (main features)
.\gradlew connectedAndroidTest --tests "com.example.mycalculatorapp.test.RunCucumberTest"

# Full test suite (includes unit tests)
.\gradlew test connectedAndroidTest
```

## Performance Tips

1. **Run Specific Features** when debugging:
   - Right-click individual `.feature` files
   - Much faster than running all tests

2. **Use Emulator** instead of physical device:
   - Usually faster
   - More consistent results

3. **Keep Emulator Running:**
   - Don't close emulator between test runs
   - Saves startup time

4. **Parallel Execution** (for CI/CD):
   ```kotlin
   // In build.gradle.kts
   android {
       testOptions {
           execution = "ANDROIDX_TEST_ORCHESTRATOR"
       }
   }
   ```

## Next Steps

1. **Sync Gradle** (important!)
2. Connect your device/emulator
3. Run `RunCucumberTest` to execute all BDD tests
4. View results in the Run panel

Your BDD test framework is now properly configured and ready to use! 🎉

