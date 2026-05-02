package com.example.mycalculatorapp.api

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import java.util.concurrent.TimeUnit

// Example for Anthropic Claude API
interface ChatbotApiService {
    @Headers("anthropic-version: 2023-06-01")
    @POST("v1/messages")
    fun getClaudeCompletion(@Body body: ClaudeRequestBody): Call<ClaudeResponse>
}

// Request body for Anthropic Claude
data class ClaudeRequestBody(
    val model: String = "claude-sonnet-4-20250514", // Updated to match your key
    val max_tokens: Int = 256,
    val temperature: Double = 0.7,
    val system: String = "You are a helpful assistant.",
    val messages: List<ClaudeMessage>
)

data class ClaudeMessage(
    val role: String, // "user" or "assistant"
    val content: String
)

// Response structure for Claude
data class ClaudeResponse(
    val content: List<ClaudeContent>
)
data class ClaudeContent(
    val text: String
)

object ChatbotApi {
    private const val BASE_URL = "https://api.anthropic.com/"
    // Remove hardcoded key, instruct user to configure it via environment or build properties
    private const val API_KEY = "YOUR_ANTHROPIC_API_KEY_HERE"

    private val authInterceptor = Interceptor { chain ->
        val request = chain.request().newBuilder()
            .addHeader("x-api-key", API_KEY)
            .addHeader("Content-Type", "application/json")
            .build()
        chain.proceed(request)
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(authInterceptor)
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(180, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .build()

    val service: ChatbotApiService = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ChatbotApiService::class.java)
}
