# Scientific Calculator Tests Added ✅

## What Was Added:

### Five New Test Scenarios in `basic_arithmetic.feature`:

#### Test 13: **Calculate sine of 30 degrees**
- Opens scientific calculator
- Enters "30"
- Presses sin button
- Verifies result contains "0.5" (sin(30°) = 0.5)

#### Test 14: **Calculate square root of 16**
- Opens scientific calculator
- Enters "16"
- Presses sqrt button
- Verifies result is "4" (√16 = 4)

#### Test 15: **Calculate 2 raised to power 3**
- Opens scientific calculator
- Enters "2"
- Presses power button (^)
- Enters "3"
- Presses equals
- Verifies result is "8" (2³ = 8)

#### Test 16: **Calculate factorial of 5**
- Opens scientific calculator
- Enters "5"
- Presses factorial button (!)
- Verifies result is "120" (5! = 120)

#### Test 17: **Calculate square of 7**
- Opens scientific calculator
- Enters "7"
- Presses square button (x²)
- Verifies result is "49" (7² = 49)

---

## Step Definitions Added (11 new):

### Navigation & Display (2)
1. **`I open the scientific calculator`** - Clicks the scientific calculator button
2. **`the scientific calculator should be displayed`** - Verifies scientific calculator screen is shown

### Number Entry (1)
3. **`I enter scientific number "{number}"`** - Enters numbers using scientific calculator keypad

### Scientific Operations (5)
4. **`I press sin button`** - Calculates sine function
5. **`I press sqrt button`** - Calculates square root
6. **`I press power button`** - Initiates power/exponent operation
7. **`I press factorial button`** - Calculates factorial
8. **`I press square button`** - Calculates square (x²)

### Action & Results (3)
9. **`I press scientific equals`** - Executes the calculation
10. **`the scientific result should be "{expected}"`** - Verifies exact result
11. **`the scientific result should contain "{expected}"`** - Verifies result contains value (for approximations)

---

## Test Scenarios Summary:

```gherkin
Scenario: Calculate sine of 30 degrees
  Given the calculator is open
  When I open the scientific calculator
  Then the scientific calculator should be displayed
  When I enter scientific number "30"
  And I press sin button
  Then the scientific result should contain "0.5"

Scenario: Calculate square root of 16
  Given the calculator is open
  When I open the scientific calculator
  Then the scientific calculator should be displayed
  When I enter scientific number "16"
  And I press sqrt button
  Then the scientific result should be "4"

Scenario: Calculate 2 raised to power 3
  Given the calculator is open
  When I open the scientific calculator
  Then the scientific calculator should be displayed
  When I enter scientific number "2"
  And I press power button
  And I enter scientific number "3"
  And I press scientific equals
  Then the scientific result should be "8"

Scenario: Calculate factorial of 5
  Given the calculator is open
  When I open the scientific calculator
  Then the scientific calculator should be displayed
  When I enter scientific number "5"
  And I press factorial button
  Then the scientific result should be "120"

Scenario: Calculate square of 7
  Given the calculator is open
  When I open the scientific calculator
  Then the scientific calculator should be displayed
  When I enter scientific number "7"
  And I press square button
  Then the scientific result should be "49"
```

---

## View IDs Used:

The tests use these view IDs from your scientific calculator layout:
- `R.id.btn_scientific` - Button to open scientific calculator (in MainActivity)
- `R.id.txt_sci_display` - Scientific calculator display
- `R.id.btn_sci_0` through `R.id.btn_sci_9` - Number buttons
- `R.id.btn_sci_dot` - Decimal point button
- `R.id.btn_sci_sin` - Sine function button
- `R.id.btn_sci_sqrt` - Square root button
- `R.id.btn_sci_power` - Power/exponent button
- `R.id.btn_sci_factorial` - Factorial button
- `R.id.btn_sci_square` - Square (x²) button
- `R.id.btn_sci_equals` - Equals button

---

## Updated Test Count:

- **Previous:** 12 test scenarios
- **Current:** **17 test scenarios**
  - 8 basic arithmetic tests
  - 1 chained operation test
  - 1 math tutor test
  - 2 currency conversion tests
  - **5 scientific calculator tests** (NEW! ✨)

---

## How to Run:

### Run all tests:
```powershell
cd C:\Users\Home\AndroidStudioProjects\Android-Calculator-App
.\gradlew connectedDebugAndroidTest
```

### Run ONLY scientific calculator tests:
```powershell
# Test 13: Sine
.\gradlew connectedDebugAndroidTest -Pandroid.testInstrumentationRunnerArguments.name="Calculate sine of 30 degrees"

# Test 14: Square root
.\gradlew connectedDebugAndroidTest -Pandroid.testInstrumentationRunnerArguments.name="Calculate square root of 16"

# Test 15: Power
.\gradlew connectedDebugAndroidTest -Pandroid.testInstrumentationRunnerArguments.name="Calculate 2 raised to power 3"

# Test 16: Factorial
.\gradlew connectedDebugAndroidTest -Pandroid.testInstrumentationRunnerArguments.name="Calculate factorial of 5"

# Test 17: Square
.\gradlew connectedDebugAndroidTest -Pandroid.testInstrumentationRunnerArguments.name="Calculate square of 7"
```

---

## Expected Test Duration:

**~30-40 seconds** for all 5 scientific calculator tests

**Total suite: ~3-4 minutes** for all 17 scenarios

---

## What Each Test Verifies:

### Test 13: Sine Function
1. ✅ Scientific calculator opens
2. ✅ Number entry works (30)
3. ✅ Sin button functions correctly
4. ✅ Trigonometric calculation is accurate
5. ✅ Result displays correctly (~0.5)

### Test 14: Square Root
1. ✅ Scientific calculator opens
2. ✅ Number entry works (16)
3. ✅ Square root button works
4. ✅ Calculation is correct (√16 = 4)
5. ✅ Result displays exactly "4"

### Test 15: Power/Exponent
1. ✅ Scientific calculator opens
2. ✅ First number entry (2)
3. ✅ Power button (^) works
4. ✅ Second number entry (3)
5. ✅ Equals executes calculation
6. ✅ Result is correct (2³ = 8)

### Test 16: Factorial
1. ✅ Scientific calculator opens
2. ✅ Number entry works (5)
3. ✅ Factorial button (!) works
4. ✅ Calculation is correct (5! = 120)
5. ✅ Result displays as "120"

### Test 17: Square
1. ✅ Scientific calculator opens
2. ✅ Number entry works (7)
3. ✅ Square button (x²) works
4. ✅ Calculation is correct (7² = 49)
5. ✅ Result displays as "49"

---

## Mathematical Operations Tested:

| Operation | Symbol | Test Input | Expected Output |
|-----------|--------|------------|-----------------|
| **Sine** | sin() | 30° | ~0.5 |
| **Square Root** | √ | 16 | 4 |
| **Power** | x^y | 2³ | 8 |
| **Factorial** | ! | 5! | 120 |
| **Square** | x² | 7² | 49 |

---

## Benefits:

1. ✅ Tests scientific calculator navigation
2. ✅ Verifies number entry in scientific mode
3. ✅ Tests trigonometric functions (sin)
4. ✅ Tests root calculations (sqrt)
5. ✅ Tests exponentiation (power)
6. ✅ Tests factorial operations
7. ✅ Tests square operations
8. ✅ Validates calculation accuracy
9. ✅ Ensures results display correctly
10. ✅ Comprehensive coverage of scientific features

---

## Test Implementation Details:

### Using `should contain` vs `should be`:
- **`should be`**: Used for exact matches (4, 8, 120, 49)
- **`should contain`**: Used for approximate results (sine returns ~0.5 with decimals)

### Thread.sleep() Usage:
- Wait times added to ensure UI updates complete
- 100ms between digit presses
- 500ms after operations
- 1000ms for activity transitions

### Number Entry:
- Each digit pressed individually
- Supports 0-9 and decimal point
- Uses scientific calculator button IDs (btn_sci_0, etc.)

---

## Status:

✅ **Test scenarios added**
✅ **Step definitions created**
✅ **All view IDs verified**
✅ **Project rebuilt successfully**
✅ **No compilation errors**
✅ **Ready to run**

---

## Complete Test Suite Overview:

**17 Total Test Scenarios:**

### Basic Operations (8)
- Addition, subtraction, multiplication, division
- Percent, clear, negative numbers, decimals

### Advanced (1)
- Chained operations

### AI Features (1)
- Math tutor with explanation

### Currency (2)
- Open converter and enter amount
- Display default currencies

### Scientific (5) ⭐ NEW!
- Sine function
- Square root
- Power/exponent
- Factorial
- Square

---

**Your test suite now includes comprehensive scientific calculator testing!** 🔬📐🎉

