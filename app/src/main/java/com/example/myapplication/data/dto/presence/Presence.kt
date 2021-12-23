package com.example.myapplication.data.dto.presence

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Presence(
    //EventFields
    val status: String? = null,
    val timestamp: Int? = null,
    //RegisterFields
    @SerialName("active_timestamp")
    val activeTimestamp: Int? = null,
    @SerialName("idle_timestamp")
    val idleTimestamp: Int? = null
)