package com.example.myapplication.presentation.models

import kotlinx.parcelize.Parcelize

@Parcelize
data class IncomeMessage(
    val user: User,
    val messageText: String,
    var reactions: List<Reaction>
) : ChatItem {
    override fun getType(): Int {
        return MessageTypes.INCOME.ordinal
    }
}