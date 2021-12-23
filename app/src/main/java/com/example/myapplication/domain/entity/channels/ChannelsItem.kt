package com.example.myapplication.domain.entity.channels

import android.os.Parcelable

interface ChannelsItem : Parcelable {
    fun getType(): Int
}