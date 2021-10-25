package com.example.myapplication.models

interface Message {
    var reactions: List<Reaction>
    fun getType(): Int
}