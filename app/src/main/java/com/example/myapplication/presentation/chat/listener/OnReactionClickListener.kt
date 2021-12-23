package com.example.myapplication.presentation.chat.listener

import com.example.myapplication.domain.entity.chat.Reaction

interface OnReactionClickListener {
    fun reactionInMessageClicked(emoji: Reaction, messagePosition: Int)
    fun reactionAppendClicked(messagePosition: Int)
}