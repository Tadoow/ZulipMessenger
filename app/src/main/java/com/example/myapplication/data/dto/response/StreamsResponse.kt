package com.example.myapplication.data.dto.response

import com.example.myapplication.data.dto.StreamDto
import kotlinx.serialization.Serializable

@Serializable
data class StreamsResponse(
    val streams: List<StreamDto>
)