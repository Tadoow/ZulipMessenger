package com.example.myapplication.data.dto.response

import com.example.myapplication.data.dto.UserDto
import kotlinx.serialization.Serializable

@Serializable
data class UsersResponse(
    val members: List<UserDto>
)