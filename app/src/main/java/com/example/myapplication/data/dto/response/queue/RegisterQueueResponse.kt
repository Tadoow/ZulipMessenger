package com.example.myapplication.data.dto.response.queue

import com.example.myapplication.data.dto.presence.Presence
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RegisterQueueResponse(

    @SerialName("queue_id")
    val id: String,

    @SerialName("last_event_id")
    val lastId: Int,

    val presences: Map<Int, Presence>? = null,

    @SerialName("server_timestamp")
    val registerQueueTime: Double? = null
)