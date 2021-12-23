package com.example.myapplication.domain.entity.chat

interface Message {
    val id: Int
    val messageText: String
    var emojiList: List<Reaction>
}