package com.example.myapplication.data.dto.event

import com.example.myapplication.data.dto.presence.PresenceInClients
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("presence")
data class PresenceEvent(

    override val id: Int,

    @SerialName("user_id")
    val userId: Int,

    val presence: PresenceInClients
) : Event()