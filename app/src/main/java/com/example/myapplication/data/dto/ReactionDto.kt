package com.example.myapplication.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReactionDto(

    @SerialName("emoji_name")
    val emojiName: String,

    @SerialName("emoji_code")
    val emojiCode: String,

    @SerialName("user_id")
    val userId: Int
)