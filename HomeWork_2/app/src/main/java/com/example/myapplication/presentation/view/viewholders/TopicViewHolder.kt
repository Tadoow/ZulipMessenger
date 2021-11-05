package com.example.myapplication.presentation.view.viewholders

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.presentation.models.ChannelsItem
import com.example.myapplication.presentation.models.Topic

class TopicViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val topic: TextView
    private val messagesCountInTopic: TextView

    init {
        topic = itemView.findViewById(R.id.topic_text_view)
        messagesCountInTopic = itemView.findViewById(R.id.messages_count_in_topic_text_view)
    }

    fun setData(data: ChannelsItem) {
        topic.text = (data as Topic).topicName
        messagesCountInTopic.text = data.chatItems.size.toString() + " mes"
        itemView.setBackgroundColor(data.backgroundColor)
    }
}