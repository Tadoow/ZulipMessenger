package com.example.myapplication.ui.people.adapter

import com.example.myapplication.domain.people.entity.User
import com.example.myapplication.ui.people.adapter.viewholder.UserViewHolder
import com.example.myapplication.ui.people.listener.OnUserClickListener

object UserItemBinder {

    fun bind(holder: UserViewHolder, user: User, userClickListener: OnUserClickListener) {
        holder.setUserName(user.userName)
        holder.setUserEmail(user.email)

        holder.itemView.setOnClickListener {
            userClickListener.userClicked(user)
        }
    }
}