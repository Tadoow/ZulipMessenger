package com.example.myapplication.presentation.emoji_list.listener

import com.example.myapplication.domain.entity.chat.Reaction

interface OnEmojiClickListener {
    fun emojiClicked(emoji: Reaction)
}