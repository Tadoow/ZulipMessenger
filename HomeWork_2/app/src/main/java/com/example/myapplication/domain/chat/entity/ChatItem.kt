package com.example.myapplication.domain.chat.entity

import android.os.Parcelable

interface ChatItem : Parcelable {
    fun getType(): Int
}