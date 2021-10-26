package com.example.myapplication.view

import com.example.myapplication.models.Reaction

interface OnReactionClickListener {
    fun reactionClicked(reaction: Reaction, messagePosition: Int)
    fun emojiClicked(emoji: String)
}