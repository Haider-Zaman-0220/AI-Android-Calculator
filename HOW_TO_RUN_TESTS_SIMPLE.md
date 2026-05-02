# How to Run Your Cucumber Tests - QUICK GUIDE

## ✅ Tests Are Now Ready and Running!

I've just executed the tests for you. They are currently running on your emulator.

## How to Run Tests Yourself:

### Method 1: PowerShell Command (Easiest)

1. **Open PowerShell** or Terminal in Android Studio

2. **Navigate to your project:**
   ```powershell
   cd C:\Users\Home\AndroidStudioProjects\Android-Calculator-App
   ```

3. **Run the tests:**
   ```powershell
   .\gradlew connectedDebugAndroidTest
   ```

4. **Wait** for tests to complete (15-20 minutes for all 81 tests)

5. **View the report:**
   - Location: `app\build\reports\androidTests\connected\debug\index.html`
   - Or run: `Start-Process "app\build\reports\androidTests\connected\debug\index.html"`

---

### Method 2: From Android Studio UI

#### Option A: Gradle Panel
1. Open **Gradle panel** (right side of Android Studio)
2. Expand: `Android-Calculator-App` → `app` → `Tasks` → `verification`
3. **Double-click** on `connectedDebugAndroidTest`
4. Watch the Run panel at the bottom

#### Option B: Run Configuration
1. Click **Run** → **Edit Configurations...**
2. Click **+** → **Android Instrumented Tests**
3. Set:
   - **Name:** `All Cucumber Tests`
   - **Module:** `app`
   - **Test:** `All in Package`
   - **Package:** `com.example.mycalculatorapp`
4. Click **OK**
5. Select configuration from dropdown
6. Click green **Run** button ▶️

---

## What Happens When Tests Run:

1. ✅ Gradle builds the app and test APKs
2. ✅ Installs them on your emulator/device
3. ✅ Starts test execution
4. ✅ You'll see the app open and tests interact with it
5. ✅ 81 test scenarios execute automatically
6. ✅ Report is generated when complete

**Watch your emulator screen** - you'll see:
- Calculator buttons being pressed
- Numbers being entered
- Screen transitions to Scientific Calculator, Currency Converter, Chatbot
- AI features being tested
- Navigation between features

---

## Run Specific Test Features:

### Run ONLY Basic Arithmetic Tests (~30 seconds):
```powershell
.\gradlew connectedDebugAndroidTest -Pandroid.testInstrumentationRunnerArguments.features=features/basic_arithmetic.feature
```

### Run ONLY Scientific Calculator Tests (~2 minutes):
```powershell
.\gradlew connectedDebugAndroidTest -Pandroid.testInstrumentationRunnerArguments.features=features/scientific_calculator.feature
```

### Run ONLY Currency Converter Tests (~1 minute):
```powershell
.\gradlew connectedDebugAndroidTest -Pandroid.testInstrumentationRunnerArguments.features=features/currency_converter.feature
```

### Run ONLY Chatbot Tests (~3 minutes):
```powershell
.\gradlew connectedDebugAndroidTest -Pandroid.testInstrumentationRunnerArguments.features=features/chatbot.feature
```

### Run ONLY Math Tutor Tests (~3 minutes):
```powershell
.\gradlew connectedDebugAndroidTest -Pandroid.testInstrumentationRunnerArguments.features=features/math_tutor.feature
```

### Run ONLY Integration Tests (~7 minutes):
```powershell
.\gradlew connectedDebugAndroidTest -Pandroid.testInstrumentationRunnerArguments.features=features/feature_integration.feature
```

---

## Prerequisites:

✅ **Emulator must be running** - Start it from Device Manager in Android Studio
✅ **Internet connection** - Required for AI features (Chatbot, Math Tutor)
✅ **Don't touch the device** - Let tests run automatically
✅ **Keep screen unlocked** - Tests may fail if screen locks

---

## View Test Results:

### While Running:
- **Logcat panel** in Android Studio shows real-time test output
- **Run panel** shows test progress
- **Emulator screen** shows the app being tested

### After Completion:
- **HTML Report:** `app\build\reports\androidTests\connected\debug\index.html`
  - Shows pass/fail status
  - Test execution time
  - Error details for failures
  - Overall statistics

---

## Common Commands:

```powershell
# Run all tests
.\gradlew connectedDebugAndroidTest

# Clean and run tests
.\gradlew clean connectedDebugAndroidTest

# Uninstall app and run tests
.\gradlew uninstallAll connectedDebugAndroidTest

# Check connected devices
adb devices

# View test report
Start-Process "app\build\reports\androidTests\connected\debug\index.html"
```

---

## Test Status: ✅ CURRENTLY RUNNING

I just executed the tests for you. They are running on your emulator right now!

**Total Tests:** 81 scenarios
**Estimated Time:** 15-20 minutes
**Status:** In Progress

You can watch the progress in:
- Android Studio Run panel (bottom)
- Emulator screen
- Logcat output

---

## Quick Troubleshooting:

### Tests won't start
```powershell
# Make sure emulator is running
adb devices

# Clean and rebuild
.\gradlew clean assembleDebug assembleDebugAndroidTest
```

### Tests fail immediately
```powershell
# Uninstall and try again
.\gradlew uninstallAll
.\gradlew connectedDebugAndroidTest
```

### Want to stop tests
- Press **Ctrl+C** in the terminal
- Or click the red **Stop** button in Android Studio

---

## That's It!

**Running tests is as simple as:**
```powershell
.\gradlew connectedDebugAndroidTest
```

The tests are fully automated and will test every feature of your calculator app! 🚀

