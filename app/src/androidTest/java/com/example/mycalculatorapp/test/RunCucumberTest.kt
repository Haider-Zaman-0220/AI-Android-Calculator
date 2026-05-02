package com.example.mycalculatorapp.test

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.*

/**
 * This is a placeholder test class.
 *
 * To run Cucumber tests, use the command line:
 * ./gradlew connectedAndroidTest
 *
 * Or in Android Studio:
 * 1. Right-click on the app module
 * 2. Select "Run 'All Tests'"
 *
 * The tests are run using the CucumberTestRunner configured in build.gradle.kts
 */
@RunWith(AndroidJUnit4::class)
class RunCucumberTest {

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.mycalculatorapp", appContext.packageName)
    }
}



