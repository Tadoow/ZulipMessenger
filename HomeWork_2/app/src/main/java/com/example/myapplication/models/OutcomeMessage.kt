package com.example.myapplication.models

data class OutcomeMessage(
    val messageText: String,
    override var reactions: List<Reaction>
) : Message {
    override fun getType(): Int {
        return MessageTypes.OUTCOME.ordinal
    }
}