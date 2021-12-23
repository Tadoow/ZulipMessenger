package com.example.myapplication.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class Narrow(
    val operand: String,
    val operator: String
)