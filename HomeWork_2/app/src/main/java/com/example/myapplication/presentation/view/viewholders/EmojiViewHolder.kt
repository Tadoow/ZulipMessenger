package com.example.myapplication.presentation.view.viewholders

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.presentation.view.listeners.OnEmojiClickListener

class EmojiViewHolder(
    itemView: View,
    private val emojiClickListener: OnEmojiClickListener
) : RecyclerView.ViewHolder(itemView) {

    private val emojiTextView: TextView

    init {
        emojiTextView = itemView.findViewById(R.id.emoji_text_view)
    }

    fun setEmoji(emoji: String) {
        emojiTextView.text = emoji
        emojiTextView.setOnClickListener { emojiClickListener.emojiClicked(emoji) }
    }
}