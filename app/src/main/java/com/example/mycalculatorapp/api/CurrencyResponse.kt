package com.example.mycalculatorapp.api

import com.google.gson.annotations.SerializedName

data class CurrencyResponse(
    @SerializedName("result")
    val result: String,
    @SerializedName("base_code")
    val baseCode: String,
    @SerializedName("rates") // Updated to match open.er-api.com
    val conversionRates: Map<String, Double>
)
