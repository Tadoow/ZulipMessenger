package com.example.myapplication.data.dto.response

import com.example.myapplication.data.dto.MessageDto
import kotlinx.serialization.Serializable

@Serializable
data class MessagesResponse(
    val messages: List<MessageDto>
)