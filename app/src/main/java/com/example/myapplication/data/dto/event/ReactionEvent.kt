package com.example.myapplication.data.dto.event

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("reaction")
data class ReactionEvent(
    override val id: Int,

    @SerialName("emoji_name")
    val emojiName: String,

    @SerialName("emoji_code")
    val emojiCode: String,

    @SerialName("user_id")
    val userId: Int,

    @SerialName("message_id")
    val messageId: Int,

    val op: String
) : Event()
