package com.example.myapplication.presentation.people.listener

import com.example.myapplication.domain.entity.people.User

interface OnUserClickListener {
    fun userClicked(user: User)
}