package com.example.myapplication.presentation.channels.viewpager.adapter.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.TopicItemBinding

class TopicViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val binding = TopicItemBinding.bind(itemView)

    fun setTopicName(topicName: String) {
        binding.topicTextView.text = topicName
    }

    fun setBackgroundColor(color: Int) {
        itemView.setBackgroundColor(color)
    }

}