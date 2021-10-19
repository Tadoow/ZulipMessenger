package com.example.myapplication.view.viewholders

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R

class EmojiViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var emojiTextView: TextView? = null

    init {
        emojiTextView = itemView.findViewById(R.id.emojiTextView)
    }

    fun setEmoji(emoji: String) {
        emojiTextView?.text = emoji
    }
}