package com.example.myapplication.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TopicDto(

    @SerialName("name")
    val topicName: String
)