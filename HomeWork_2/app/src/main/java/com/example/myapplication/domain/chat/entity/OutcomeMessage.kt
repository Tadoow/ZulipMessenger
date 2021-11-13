package com.example.myapplication.domain.chat.entity

import kotlinx.parcelize.Parcelize

@Parcelize
data class OutcomeMessage(
    val messageText: String,
    var reactions: List<Reaction>
) : ChatItem {
    override fun getType(): Int {
        return MessageTypes.OUTCOME.ordinal
    }
}