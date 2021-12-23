package com.example.myapplication.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MessageDto(

    val id: Int,

    @SerialName("content")
    val text: String,

    val reactions: List<ReactionDto>,

    @SerialName("timestamp")
    val time: Long,

    @SerialName("sender_id")
    val senderId: Int,

    @SerialName("sender_full_name")
    val senderName: String,

    @SerialName("sender_email")
    val senderEmail: String,

    @SerialName("avatar_url")
    val avatar: String,
)