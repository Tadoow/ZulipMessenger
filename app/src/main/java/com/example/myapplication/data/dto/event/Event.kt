package com.example.myapplication.data.dto.event

import kotlinx.serialization.Serializable

@Serializable
sealed class Event {
    abstract val id: Int
}