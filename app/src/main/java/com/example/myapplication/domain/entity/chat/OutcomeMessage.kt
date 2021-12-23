package com.example.myapplication.domain.entity.chat

import kotlinx.parcelize.Parcelize

@Parcelize
data class OutcomeMessage(
    override val id: Int,
    override val messageText: String,
    override var emojiList: List<Reaction>
) : ChatItem(), Message