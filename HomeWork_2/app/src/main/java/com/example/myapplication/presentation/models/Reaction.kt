package com.example.myapplication.presentation.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Reaction(
    val emoji: String,
    var userId: List<Int>,
    var reactionClickState: Boolean = false
) : Parcelable {
    fun getClicksCount(): Int {
        return userId.size
    }
}