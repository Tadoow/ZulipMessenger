package com.example.myapplication.ui.chat.listener

interface OnMessageClickListener {
    fun addEmojiClicked(messagePosition: Int)
    fun messageLongClicked(messagePosition: Int): Boolean
}