package com.example.myapplication.model

class IncomeMessage(val senderName: String, val messageText: String) : Message {
    override fun getType(): Int {
        return MessageTypes.INCOME.ordinal
    }
}