package com.example.myapplication.view

interface OnMessageClickListener {
    fun addEmojiViewClicked(messagePosition: Int)
    fun messageViewClicked(messagePosition: Int): Boolean
}