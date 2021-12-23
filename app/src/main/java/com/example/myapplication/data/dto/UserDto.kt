package com.example.myapplication.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserDto(

    @SerialName("user_id")
    val id: Int,

    @SerialName("full_name")
    val name: String,

    val email: String,

    @SerialName("avatar_url")
    val avatar: String,

    @SerialName("is_active")
    val activation: Boolean,

    @SerialName("is_bot")
    val bot: Boolean
)