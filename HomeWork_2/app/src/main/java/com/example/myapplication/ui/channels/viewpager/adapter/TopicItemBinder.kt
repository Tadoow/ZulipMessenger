package com.example.myapplication.ui.channels.viewpager.adapter

import com.example.myapplication.domain.channels.entity.Topic
import com.example.myapplication.ui.channels.viewpager.adapter.viewholder.TopicViewHolder
import com.example.myapplication.ui.channels.viewpager.listener.OnTopicClickListener

object TopicItemBinder {

    fun bind(holder: TopicViewHolder, topic: Topic, topicClickListener: OnTopicClickListener) {
        holder.setTopicName(topic.topicName)
        holder.setMessagesCount(topic.chatItems.size.toString())
        holder.setBackgroundColor(topic.backgroundColor)

        holder.itemView.setOnClickListener {
            topicClickListener.topicClicked(topic)
        }
    }
}