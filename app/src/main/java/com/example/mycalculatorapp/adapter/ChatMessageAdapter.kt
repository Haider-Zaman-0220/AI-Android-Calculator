package com.example.mycalculatorapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mycalculatorapp.R
import io.noties.markwon.Markwon

sealed class ChatMessage(val text: String) {
    class User(text: String) : ChatMessage(text)
    class Bot(text: String) : ChatMessage(text)
    class Loading : ChatMessage("")
}

class ChatMessageAdapter(private val messages: MutableList<ChatMessage>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        private const val TYPE_USER = 0
        private const val TYPE_BOT = 1
        private const val TYPE_LOADING = 2
    }

    private var markwon: Markwon? = null

    fun setMarkwon(markwon: Markwon) {
        this.markwon = markwon
    }

    override fun getItemViewType(position: Int): Int {
        return when (messages[position]) {
            is ChatMessage.User -> TYPE_USER
            is ChatMessage.Bot -> TYPE_BOT
            is ChatMessage.Loading -> TYPE_LOADING
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_USER -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_chat_user, parent, false)
                UserViewHolder(view)
            }
            TYPE_LOADING -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_chat_loading, parent, false)
                LoadingViewHolder(view)
            }
            else -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_chat_bot, parent, false)
                BotViewHolder(view)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = messages[position]
        when (holder) {
            is UserViewHolder -> {
                if (message is ChatMessage.User) {
                    holder.tvMessage.text = message.text
                }
            }
            is BotViewHolder -> {
                if (message is ChatMessage.Bot) {
                    if (markwon != null) {
                        markwon!!.setMarkdown(holder.tvMessage, message.text)
                    } else {
                        holder.tvMessage.text = message.text
                    }
                }
            }
            is LoadingViewHolder -> {
                holder.startAnimation()
            }
        }
    }

    override fun getItemCount(): Int = messages.size

    fun addMessage(message: ChatMessage) {
        messages.add(message)
        notifyItemInserted(messages.size - 1)
    }

    fun removeLastMessage() {
        if (messages.isNotEmpty()) {
            messages.removeAt(messages.size - 1)
            notifyItemRemoved(messages.size)
        }
    }

    class UserViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvMessage: TextView = view.findViewById(R.id.tv_user_message)
    }

    class BotViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvMessage: TextView = view.findViewById(R.id.tv_bot_message)
    }

    class LoadingViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val dot1: View = view.findViewById(R.id.dot1)
        private val dot2: View = view.findViewById(R.id.dot2)
        private val dot3: View = view.findViewById(R.id.dot3)

        init {
            // Make dots circular
            listOf(dot1, dot2, dot3).forEach { dot ->
                dot.background = android.graphics.drawable.GradientDrawable().apply {
                    shape = android.graphics.drawable.GradientDrawable.OVAL
                    setColor(android.graphics.Color.parseColor("#666666"))
                }
            }
        }

        fun startAnimation() {
            val animation = android.view.animation.AnimationUtils.loadAnimation(itemView.context, R.anim.typing_dot_anim)

            dot1.startAnimation(animation)
            dot2.postDelayed({
                dot2.startAnimation(animation)
            }, 200)
            dot3.postDelayed({
                dot3.startAnimation(animation)
            }, 400)
        }
    }
}
