package com.example.myapplication.presentation.channels.viewpager.listener

import com.example.myapplication.domain.entity.channels.Topic

interface OnTopicClickListener {
    fun topicClicked(topic: Topic)
}