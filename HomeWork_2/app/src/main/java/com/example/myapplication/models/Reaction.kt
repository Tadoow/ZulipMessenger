package com.example.myapplication.models

data class Reaction(
    val emoji: String,
    var user_id: List<Int>,
) {
    var reactionClickState: Boolean = false

    fun getClicksCount(): Int {
        return user_id.size
    }
}