package com.example.myapplication.ui.chat.listener

import com.example.myapplication.domain.chat.entity.Reaction

interface OnReactionClickListener {
    fun reactionInMessageClicked(reaction: Reaction, messagePosition: Int)
}