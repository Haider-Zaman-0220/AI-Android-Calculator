package com.example.mycalculatorapp

import android.util.Log
import com.example.mycalculatorapp.api.ChatbotApi
import com.example.mycalculatorapp.api.ClaudeMessage
import com.example.mycalculatorapp.api.ClaudeRequestBody
import com.example.mycalculatorapp.api.ClaudeResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class MathTutorService {

    private val apiService = ChatbotApi.service

    suspend fun getStepByStepExplanation(expression: String, result: String): String {
        return withContext(Dispatchers.IO) {
            try {
                val prompt = """
                    Explain this math calculation in a simple, step-by-step way:
                    
                    Expression: $expression
                    Result: $result
                    
                    Please provide:
                    1. What operation is being performed
                    2. Step-by-step breakdown of the calculation
                    3. A simple explanation suitable for learning
                    
                    Keep it concise and under 200 words.
                """.trimIndent()

                val requestBody = ClaudeRequestBody(
                    model = "claude-sonnet-4-20250514",
                    system = "You are a friendly math tutor that explains calculations in simple terms. Use clear language and break down steps.",
                    messages = listOf(ClaudeMessage("user", prompt)),
                    temperature = 0.7,
                    max_tokens = 500
                )

                val response: Response<ClaudeResponse> = apiService.getClaudeCompletion(requestBody).execute()

                if (response.isSuccessful) {
                    val explanation = response.body()?.content?.firstOrNull()?.text
                    explanation ?: "I calculated: $expression = $result"
                } else {
                    Log.e("MathTutor", "API error: ${response.errorBody()?.string()}")
                    getOfflineExplanation(expression, result)
                }
            } catch (e: Exception) {
                Log.e("MathTutor", "Exception: ${e.message}")
                getOfflineExplanation(expression, result)
            }
        }
    }

    private fun getOfflineExplanation(expression: String, result: String): String {
        // Fallback explanations when API is unavailable
        val parts = expression.split(Regex("[+\\-*/]"))
        val operator = expression.firstOrNull { it in listOf('+', '-', '*', '/') }

        return when (operator) {
            '+' -> {
                "🔢 Addition\n\nI added ${parts[0]} and ${parts[1]} together.\n\n${parts[0]} + ${parts[1]} = $result\n\nThink of it as combining two numbers to get a larger sum!"
            }
            '-' -> {
                "🔢 Subtraction\n\nI subtracted ${parts[1]} from ${parts[0]}.\n\n${parts[0]} - ${parts[1]} = $result\n\nSubtraction means taking away!"
            }
            '*' -> {
                "🔢 Multiplication\n\nI multiplied ${parts[0]} by ${parts[1]}.\n\n${parts[0]} × ${parts[1]} = $result\n\nMultiplication is repeated addition: ${parts[0]} added to itself ${parts[1]} times!"
            }
            '/' -> {
                "🔢 Division\n\nI divided ${parts[0]} by ${parts[1]}.\n\n${parts[0]} ÷ ${parts[1]} = $result\n\nDivision splits a number into equal parts!"
            }
            else -> {
                "📝 Calculation\n\n$expression = $result\n\nYour calculation has been completed!"
            }
        }
    }
}

