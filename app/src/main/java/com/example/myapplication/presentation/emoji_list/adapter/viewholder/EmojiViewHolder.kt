package com.example.myapplication.presentation.emoji_list.adapter.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.EmojiItemBinding
import com.example.myapplication.domain.entity.chat.Reaction
import com.example.myapplication.presentation.emoji_list.listener.OnEmojiClickListener

class EmojiViewHolder(
    itemView: View,
    private val emojiClickListener: OnEmojiClickListener
) : RecyclerView.ViewHolder(itemView) {

    private val binding = EmojiItemBinding.bind(itemView)

    fun setEmoji(emoji: Reaction) {
        binding.emojiTextView.text = convertEmojiCode(emoji.emojiCode)
        binding.emojiTextView.setOnClickListener { emojiClickListener.emojiClicked(emoji) }
    }

    private fun convertEmojiCode(emojiCode: String): String {
        val separator = emojiCode.indexOf("-")
        return if (separator == -1) {
            String(Character.toChars(emojiCode.toInt(16)))
        } else {
            String(Character.toChars(emojiCode.substring(0, separator).toInt(16))) +
                    String(Character.toChars(emojiCode.substring(separator + 1).toInt(16)))
        }
    }

}