package com.example.myapplication.domain.entity.chat

import com.example.myapplication.domain.entity.people.User
import kotlinx.parcelize.Parcelize

@Parcelize
data class IncomeMessage(
    override val id: Int,
    val user: User,
    override val messageText: String,
    override var emojiList: List<Reaction>
) : ChatItem(), Message