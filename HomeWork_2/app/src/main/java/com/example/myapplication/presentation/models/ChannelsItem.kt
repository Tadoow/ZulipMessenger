package com.example.myapplication.presentation.models

import android.os.Parcelable

interface ChannelsItem : Parcelable {
    fun getType(): Int
}