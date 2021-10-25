package com.example.myapplication.models

data class IncomeMessage(
    val senderName: String,
    val messageText: String,
    override var reactions: List<Reaction>
) : Message {
    override fun getType(): Int {
        return MessageTypes.INCOME.ordinal
    }
}