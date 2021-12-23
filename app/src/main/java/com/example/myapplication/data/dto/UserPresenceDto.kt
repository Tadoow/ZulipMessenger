package com.example.myapplication.data.dto

import com.example.myapplication.data.dto.presence.PresenceInClients
import kotlinx.serialization.Serializable

@Serializable
data class UserPresenceDto(
    val presence: PresenceInClients
)