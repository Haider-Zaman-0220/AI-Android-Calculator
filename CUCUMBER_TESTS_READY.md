# Cucumber Test Setup - COMPLETE GUIDE

## Current Status: ✅ TESTS ARE CONFIGURED AND READY TO RUN

I've successfully configured your Cucumber test framework. Here's what was done:

### Files Created/Modified:

1. **CucumberTestOptions.kt** ✅
   - Location: `app/src/androidTest/java/com/example/mycalculatorapp/CucumberTestOptions.kt`
   - Contains the `@CucumberOptions` annotation required by the test runner
   - Specifies features folder and glue package

2. **CucumberTestRunner.kt** ✅
   - Updated to include glue and features configuration in onCreate
   - Properly configured to find test scenarios

3. **build.gradle.kts** ✅
   - Added package argument to testInstrumentationRunnerArguments
   - Tells the runner where to find the CucumberOptions class

### What You Need to Do to Run Tests:

## STEP-BY-STEP GUIDE TO RUN TESTS:

### Option 1: Command Line (Recommended)

1. **Open PowerShell or Terminal**

2. **Navigate to your project:**
   ```powershell
   cd C:\Users\Home\AndroidStudioProjects\Android-Calculator-App
   ```

3. **Make sure a device/emulator is running**
   - Check in Android Studio Device Manager
   - OR run: `adb devices` (should show at least one device)

4. **Run the tests:**
   ```powershell
   .\gradlew connectedDebugAndroidTest
   ```

5. **Wait for completion** (this may take 5-10 minutes for all tests)

6. **View results:**
   - Open: `app\build\reports\androidTests\connected\debug\index.html` in your browser

### Option 2: Android Studio UI

1. **Open Run Configuration:**
   - Click "Run" menu → "Edit Configurations..."

2. **Create New Configuration:**
   - Click the "+" button
   - Select "Android Instrumented Tests"

3. **Configure:**
   - **Name:** `Cucumber Tests`
   - **Module:** `app`
   - **Test:** Select "All in Package"
   - **Package:** `com.example.mycalculatorapp`
   - **Instrumentation runner:** Should auto-select `CucumberTestRunner`

4. **Apply and Run:**
   - Click "OK"
   - Make sure emulator is running
   - Select "Cucumber Tests" from the dropdown
   - Click the green Run button ▶️

### Option 3: Gradle Panel in Android Studio

1. **Open Gradle Panel** (usually on the right side)

2. **Navigate to:**
   ```
   Android-Calculator-App
   └── app
       └── Tasks
           └── verification
               └── connectedDebugAndroidTest
   ```

3. **Double-click** `connectedDebugAndroidTest`

4. **Watch** the Run panel at the bottom for progress

## Your Test Suite:

You have **6 feature files** ready to run:

### 1. basic_arithmetic.feature
- Add two numbers
- Subtract two numbers  
- Multiply two numbers
- Divide two numbers
- Complex calculation with multiple operations

### 2. scientific_calculator.feature
- Navigate to scientific calculator
- Calculate sine function
- Calculate cosine function
- Calculate tangent function
- Calculate square root
- And more...

### 3. currency_converter.feature
- Open currency converter
- Convert USD to EUR
- Convert between different currencies
- Validate conversion results

### 4. chatbot.feature
- Open chatbot
- Send message to chatbot
- Receive response from chatbot
- Ask calculation questions

### 5. math_tutor.feature
- Open math tutor bubble
- Perform calculation with tutor active
- See explanation in tutor window
- Close math tutor

### 6. feature_integration.feature (10 scenarios!)
- Navigate between all calculator features
- Math tutor integration with scientific calculator
- Perform calculations and ask chatbot
- Return to main calculator from each feature
- Complex workflows across multiple features

## Total Test Coverage:

- **35+ test scenarios** across all features
- **Full navigation testing** between all activities
- **AI feature testing** (Chatbot and Math Tutor)
- **Scientific calculator functions**
- **Currency conversion**
- **Integration scenarios**

## Expected Test Duration:

- **Basic arithmetic:** ~30 seconds
- **Scientific calculator:** ~1 minute
- **Currency converter:** ~1 minute
- **Chatbot:** ~2-3 minutes (requires API)
- **Math tutor:** ~2-3 minutes (requires API)
- **Feature integration:** ~5-7 minutes
- **TOTAL:** ~15-20 minutes for all tests

## Troubleshooting:

### "No devices found"
**Solution:** Start an Android emulator in Android Studio (Device Manager → Play button)

### "Tests keep failing"
**Solution:** 
1. Make sure you have internet connection (for AI features)
2. Make sure the app isn't already running on the device
3. Try: `.\gradlew uninstallAll` then run tests again

### "Build failed"
**Solution:**
```powershell
.\gradlew clean
.\gradlew assembleDebug assembleDebugAndroidTest
.\gradlew connectedDebugAndroidTest
```

### "Tests are too slow"
**Solution:** Run specific feature files:
```powershell
.\gradlew connectedDebugAndroidTest -Pandroid.testInstrumentationRunnerArguments.features=features/basic_arithmetic.feature
```

## What Tests Will Do:

When you run the tests, you'll see:

1. **App installs** on your emulator/device
2. **Tests automatically navigate** through your app
3. **Buttons get clicked** automatically
4. **Text gets entered** in fields
5. **Results get verified** automatically
6. **AI features get tested** (chatbot, math tutor)
7. **Report gets generated** showing pass/fail for each scenario

## Viewing Test Results:

After tests complete, open the HTML report:

**Location:** `app\build\reports\androidTests\connected\debug\index.html`

The report shows:
- ✅ Which tests passed
- ❌ Which tests failed
- ⏱️ How long each test took
- 📊 Overall pass rate
- 📝 Error details for failures

## Important Notes:

1. **Don't touch the device** while tests are running
2. **Keep screen on** - tests may fail if screen locks
3. **Internet required** - for chatbot and math tutor tests
4. **API key must be valid** - Claude API for AI features
5. **First run is slowest** - subsequent runs are faster

## Next Steps:

1. ✅ Start an emulator
2. ✅ Run: `.\gradlew connectedDebugAndroidTest`
3. ✅ Wait for completion
4. ✅ Open the HTML report
5. ✅ Review test results

## Summary:

✅ **All test files created**
✅ **All step definitions ready**
✅ **Test runner configured**
✅ **Build configuration updated**
✅ **Feature files validated**

**YOU'RE READY TO RUN TESTS!** Just execute the gradle command and watch your app being automatically tested!

---

## Quick Commands Reference:

```powershell
# Run ALL tests
.\gradlew connectedDebugAndroidTest

# Run ONLY basic arithmetic
.\gradlew connectedDebugAndroidTest -Pandroid.testInstrumentationRunnerArguments.features=features/basic_arithmetic.feature

# Clean and rebuild
.\gradlew clean assembleDebug assembleDebugAndroidTest

# Uninstall app from device
.\gradlew uninstallAll

# Check connected devices
adb devices

# View test report (after running)
Start-Process "app\build\reports\androidTests\connected\debug\index.html"
```

Good luck with your testing! 🚀

