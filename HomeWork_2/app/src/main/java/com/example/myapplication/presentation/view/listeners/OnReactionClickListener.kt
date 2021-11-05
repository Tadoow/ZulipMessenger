package com.example.myapplication.presentation.view.listeners

import com.example.myapplication.presentation.models.Reaction

interface OnReactionClickListener {
    fun reactionInMessageClicked(reaction: Reaction, messagePosition: Int)
}