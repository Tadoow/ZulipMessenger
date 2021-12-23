package com.example.myapplication.data.dto.presence

import kotlinx.serialization.Serializable

@Serializable
data class PresenceInClients(
    val aggregated: Presence? = null,
    val website: Presence
)