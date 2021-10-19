package com.example.myapplication.model

class OutcomeMessage(val messageText: String) : Message {
    override fun getType(): Int {
        return MessageTypes.OUTCOME.ordinal
    }
}