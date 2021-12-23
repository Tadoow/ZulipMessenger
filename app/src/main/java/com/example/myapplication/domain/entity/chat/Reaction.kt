package com.example.myapplication.domain.entity.chat

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Reaction(
    val emojiName: String,
    val emojiCode: String,
    var reactedUsersIds: List<Int>,
    var reactionClickState: Boolean = false
) : Parcelable {
    fun getReactionsCount(): Int {
        return reactedUsersIds.size
    }
}