package com.example.myapplication.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StreamDto(

    val name: String,

    @SerialName("stream_id")
    val id: Int,

    val color: String = "#2A9D8F"
)