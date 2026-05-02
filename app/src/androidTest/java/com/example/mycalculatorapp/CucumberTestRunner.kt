package com.example.mycalculatorapp

import android.os.Bundle
import io.cucumber.android.runner.CucumberAndroidJUnitRunner

class CucumberTestRunner : CucumberAndroidJUnitRunner() {
    override fun onCreate(arguments: Bundle?) {
        arguments?.putString("plugin", "pretty")
        arguments?.putString("glue", "com.example.mycalculatorapp")
        arguments?.putString("features", "features")
        super.onCreate(arguments)
    }
}

