package com.example.myapplication.ui.emoji_list.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.EmojiItemBinding
import com.example.myapplication.ui.emoji_list.listener.OnEmojiClickListener

class EmojiViewHolder(
    itemView: View,
    private val emojiClickListener: OnEmojiClickListener
) : RecyclerView.ViewHolder(itemView) {

    private val binding = EmojiItemBinding.bind(itemView)

    fun setEmoji(emoji: String) {
        binding.emojiTextView.text = emoji
        binding.emojiTextView.setOnClickListener { emojiClickListener.emojiClicked(emoji) }
    }
}