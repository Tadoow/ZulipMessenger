package com.example.myapplication.ui.emoji_list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.ui.emoji_list.listener.OnEmojiClickListener
import com.example.myapplication.ui.emoji_list.viewholder.EmojiViewHolder

class EmojiAdapter(
    private val emojiList: List<String>,
    private val emojiClickListener: OnEmojiClickListener
) : RecyclerView.Adapter<EmojiViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmojiViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.emoji_item, parent, false)
        return EmojiViewHolder(itemView, emojiClickListener)
    }

    override fun onBindViewHolder(holder: EmojiViewHolder, position: Int) {
        holder.setEmoji(emojiList[position])
    }

    override fun getItemCount(): Int = emojiList.size
}