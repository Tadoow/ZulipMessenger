package com.example.myapplication.ui.people.listener

import com.example.myapplication.domain.people.entity.User

interface OnUserClickListener {
    fun userClicked(user: User)
}