package com.example.myapplication.presentation.emoji_list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.domain.entity.chat.Reaction
import com.example.myapplication.presentation.emoji_list.adapter.viewholder.EmojiViewHolder
import com.example.myapplication.presentation.emoji_list.listener.OnEmojiClickListener

class EmojiAdapter(
    private val emojiList: List<Reaction>,
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