package com.example.myapplication.ui.channels.viewpager.listener

import com.example.myapplication.domain.channels.entity.ChannelsItem

interface OnTopicClickListener {
    fun topicClicked(channelsItem: ChannelsItem)
}