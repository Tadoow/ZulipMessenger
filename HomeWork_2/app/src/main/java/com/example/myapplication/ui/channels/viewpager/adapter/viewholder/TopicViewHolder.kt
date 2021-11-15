package com.example.myapplication.ui.channels.viewpager.adapter.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.databinding.TopicItemBinding

class TopicViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val binding = TopicItemBinding.bind(itemView)

    fun setTopicName(topicName: String) {
        binding.topicTextView.text = topicName
    }

    fun setMessagesCount(messagesCount: String) {
        binding.messagesCountInTopicTextView.text =
            itemView.resources.getString(R.string.messages_count, messagesCount)
    }

    fun setBackgroundColor(color: Int) {
        itemView.setBackgroundColor(color)
    }
}