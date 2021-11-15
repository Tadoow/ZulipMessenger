package com.example.myapplication.domain.channels.entity

import android.os.Parcelable
import androidx.annotation.ColorInt
import com.example.myapplication.domain.chat.entity.ChatItem
import kotlinx.parcelize.Parcelize

@Parcelize
data class Topic(
    val topicName: String,
    val streamHostName: String,
    @ColorInt
    val backgroundColor: Int,
    val chatItems: List<ChatItem>
) : ChannelsItem, Parcelable {
    override fun getType(): Int {
        return ChannelTypes.TOPIC.ordinal
    }
}