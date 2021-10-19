package com.example.myapplication.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.view.viewholders.EmojiViewHolder

class EmojiAdapter(private var emojiList: List<String>) : RecyclerView.Adapter<EmojiViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmojiViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.emoji_item, parent, false)
        return EmojiViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: EmojiViewHolder, position: Int) {
        holder.setEmoji(emojiList[position])
    }

    override fun getItemCount(): Int = emojiList.size
}