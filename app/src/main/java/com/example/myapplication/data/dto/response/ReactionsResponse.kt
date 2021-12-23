package com.example.myapplication.data.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReactionsResponse(
    @SerialName("name_to_codepoint")
    val nameToCodePoint: Map<String, String>
)