package com.example.myapplication.data.dto.response.queue

import com.example.myapplication.data.dto.event.Event
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EventsQueueResponse(

    val events: List<Event>,

    @SerialName("queue_id")
    val id: String
)