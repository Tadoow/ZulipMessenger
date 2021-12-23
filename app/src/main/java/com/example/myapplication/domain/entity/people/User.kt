package com.example.myapplication.domain.entity.people

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val userId: Int,
    val userName: String,
    val email: String,
    var status: String = "offline",
    val avatar: String
) : Parcelable