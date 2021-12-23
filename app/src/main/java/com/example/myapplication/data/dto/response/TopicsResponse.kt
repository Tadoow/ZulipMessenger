package com.example.myapplication.data.dto.response

import com.example.myapplication.data.dto.TopicDto
import kotlinx.serialization.Serializable

@Serializable
data class TopicsResponse(
    val topics: List<TopicDto>
)