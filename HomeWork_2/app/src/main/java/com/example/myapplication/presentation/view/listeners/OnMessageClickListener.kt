package com.example.myapplication.presentation.view.listeners

interface OnMessageClickListener {
    fun addEmojiClicked(messagePosition: Int)
    fun messageLongClicked(messagePosition: Int): Boolean
}