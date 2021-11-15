package com.example.myapplication.domain.channels.entity

import android.os.Parcelable

interface ChannelsItem : Parcelable {
    fun getType(): Int
}