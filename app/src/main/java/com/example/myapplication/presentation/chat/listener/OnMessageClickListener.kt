package com.example.myapplication.presentation.chat.listener

interface OnMessageClickListener {
    fun messageLongClicked(messagePosition: Int): Boolean
}