package com.example.myapplication.presentation.channels.viewpager.adapter

import com.example.myapplication.domain.entity.channels.Topic
import com.example.myapplication.presentation.channels.viewpager.adapter.viewholder.TopicViewHolder
import com.example.myapplication.presentation.channels.viewpager.listener.OnTopicClickListener

object TopicItemBinder {

    fun bind(holder: TopicViewHolder, topic: Topic, topicClickListener: OnTopicClickListener) {
        holder.setTopicName(topic.topicName)
        holder.setBackgroundColor(topic.backgroundColor)

        holder.itemView.setOnClickListener {
            topicClickListener.topicClicked(topic)
        }
    }

}