package com.example.myapplication.data.dto.event

import com.example.myapplication.data.dto.MessageDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("message")
data class MessageEvent(
    override val id: Int,
    val message: MessageDto
) : Event()