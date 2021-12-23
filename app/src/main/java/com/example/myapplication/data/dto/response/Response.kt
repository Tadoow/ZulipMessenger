package com.example.myapplication.data.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class Response(
    val id: Int? = null,
    val msg: String,
    val result: String,
    val code: String? = null
)