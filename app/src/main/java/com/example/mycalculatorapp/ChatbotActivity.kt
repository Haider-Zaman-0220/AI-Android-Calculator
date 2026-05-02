package com.example.mycalculatorapp

import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mycalculatorapp.adapter.ChatMessage
import com.example.mycalculatorapp.adapter.ChatMessageAdapter
import com.example.mycalculatorapp.api.ClaudeRequestBody
import com.example.mycalculatorapp.api.ClaudeMessage
import com.example.mycalculatorapp.api.ClaudeResponse
import com.example.mycalculatorapp.api.ClaudeContent
import com.example.mycalculatorapp.api.ChatbotApi
import com.example.mycalculatorapp.api.ChatbotApiService
import io.noties.markwon.Markwon
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ChatbotActivity : AppCompatActivity() {
    private lateinit var rvChatMessages: RecyclerView
    private lateinit var etChatInput: EditText
    private lateinit var btnSend: ImageButton
    private lateinit var btnBack: ImageButton
    private lateinit var emptyStateLayout: android.widget.LinearLayout
    private lateinit var adapter: ChatMessageAdapter
    private val messages = mutableListOf<ChatMessage>()
    private val apiService: ChatbotApiService by lazy {
        ChatbotApi.service
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.chatbot_activity)

        rvChatMessages = findViewById(R.id.rv_chat_messages)
        etChatInput = findViewById(R.id.et_chat_input)
        btnSend = findViewById(R.id.btn_send)
        btnBack = findViewById(R.id.btn_back)
        emptyStateLayout = findViewById(R.id.empty_state_layout)

        adapter = ChatMessageAdapter(messages)
        val markwon = Markwon.create(this)
        adapter.setMarkwon(markwon)
        rvChatMessages.adapter = adapter
        rvChatMessages.layoutManager = LinearLayoutManager(this)

        btnSend.setOnClickListener {
            val userMessage = etChatInput.text.toString().trim()
            if (userMessage.isNotEmpty()) {
                // Hide empty state and show chat on first message
                if (emptyStateLayout.visibility == android.view.View.VISIBLE) {
                    emptyStateLayout.visibility = android.view.View.GONE
                    rvChatMessages.visibility = android.view.View.VISIBLE
                }

                adapter.addMessage(ChatMessage.User(userMessage))
                etChatInput.text.clear()
                rvChatMessages.scrollToPosition(adapter.itemCount - 1)
                sendMessageToApi(userMessage)
            }
        }

        btnBack.setOnClickListener {
            finish()
        }
    }

    private fun sendMessageToApi(userMessage: String) {
        // Show loading indicator
        adapter.addMessage(ChatMessage.Loading())
        rvChatMessages.scrollToPosition(adapter.itemCount - 1)

        val requestBody = ClaudeRequestBody(
            model = "claude-sonnet-4-20250514",
            system = "You are a helpful assistant.",
            messages = listOf(
                ClaudeMessage("user", userMessage)
            ),
            temperature = 0.7,
            max_tokens = 4096
        )
        apiService.getClaudeCompletion(requestBody).enqueue(object : Callback<ClaudeResponse> {
            override fun onResponse(call: Call<ClaudeResponse>, response: Response<ClaudeResponse>) {
                // Remove loading indicator
                adapter.removeLastMessage()

                if (response.isSuccessful) {
                    val botReply = response.body()?.content?.firstOrNull()?.text ?: "No response"
                    adapter.addMessage(ChatMessage.Bot(botReply))
                } else {
                    val errorBody = response.errorBody()?.string() ?: "Unknown error"
                    adapter.addMessage(ChatMessage.Bot("API Error: $errorBody"))
                }
                rvChatMessages.scrollToPosition(adapter.itemCount - 1)
            }

            override fun onFailure(call: Call<ClaudeResponse>, t: Throwable) {
                // Remove loading indicator
                adapter.removeLastMessage()

                val errorMessage = when {
                    t is java.net.SocketTimeoutException -> "Request timed out. The AI is taking longer than expected. Please try again with a shorter question."
                    t is java.net.UnknownHostException -> "No internet connection. Please check your network."
                    t is java.io.IOException -> "Network error: ${t.message}"
                    else -> "Error: ${t.message}"
                }

                adapter.addMessage(ChatMessage.Bot(errorMessage))
                rvChatMessages.scrollToPosition(adapter.itemCount - 1)
            }
        })
    }
}
