package com.example.mycalculatorapp.model

import java.io.Serializable

data class CurrencyItem(
    val code: String,
    val name: String,
    val flag: String
) : Serializable
