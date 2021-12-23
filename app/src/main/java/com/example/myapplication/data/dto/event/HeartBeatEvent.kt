package com.example.myapplication.data.dto.event

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("heartbeat")
data class HeartBeatEvent(
    override val id: Int
) : Event()