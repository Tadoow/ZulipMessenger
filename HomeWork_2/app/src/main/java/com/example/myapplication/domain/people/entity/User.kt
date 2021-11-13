package com.example.myapplication.domain.people.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val userId: Int,
    val userName: String,
    val email: String,
    var online: Boolean
) : Parcelable