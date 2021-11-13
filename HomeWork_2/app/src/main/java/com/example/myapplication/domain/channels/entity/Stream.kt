package com.example.myapplication.domain.channels.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Stream(
    val streamName: String,
    val topics: List<Topic>,
    var isExpanded: Boolean = false
) : ChannelsItem, Parcelable {
    override fun getType(): Int {
        return ChannelTypes.STREAM.ordinal
    }
}