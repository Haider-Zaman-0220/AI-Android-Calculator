package com.example.mycalculatorapp.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ExchangeRateApi {
    @GET("latest/{base}")
    suspend fun getLatestRates(@Path("base") base: String): Response<CurrencyResponse>
}
