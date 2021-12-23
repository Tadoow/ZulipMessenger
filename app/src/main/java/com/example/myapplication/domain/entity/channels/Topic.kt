package com.example.myapplication.domain.entity.channels

import android.os.Parcelable
import androidx.annotation.ColorInt
import kotlinx.parcelize.Parcelize

@Parcelize
data class Topic(
    val topicName: String,
    val streamHostName: String,
    @ColorInt
    val backgroundColor: Int
) : ChannelsItem, Parcelable {
    override fun getType(): Int {
        return ChannelTypes.TOPIC.ordinal
    }
}