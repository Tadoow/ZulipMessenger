package com.example.myapplication.presentation.view.listeners

import com.example.myapplication.presentation.models.ChannelsItem

interface OnTopicClickListener {
    fun topicClicked(channelsItem: ChannelsItem)
}