package com.example.myapplication.domain.chat.entity

import com.example.myapplication.domain.people.entity.User
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