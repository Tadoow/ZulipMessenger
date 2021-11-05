package com.example.myapplication.presentation.models

import android.os.Parcelable

interface ChatItem : Parcelable {
    fun getType(): Int
}