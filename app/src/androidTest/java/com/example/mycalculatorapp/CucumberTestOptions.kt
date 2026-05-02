package com.example.mycalculatorapp

import io.cucumber.junit.CucumberOptions

@CucumberOptions(
    features = ["features"],
    glue = ["com.example.mycalculatorapp"],
    plugin = ["pretty", "html:build/cucumber-reports/cucumber.html"]
)
class CucumberTestOptions

