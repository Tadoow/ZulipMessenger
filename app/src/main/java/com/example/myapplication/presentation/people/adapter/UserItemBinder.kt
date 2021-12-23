package com.example.myapplication.presentation.people.adapter

import androidx.core.content.res.ResourcesCompat
import com.example.myapplication.R
import com.example.myapplication.domain.entity.people.User
import com.example.myapplication.presentation.people.adapter.viewholder.UserViewHolder
import com.example.myapplication.presentation.people.listener.OnUserClickListener

object UserItemBinder {

    fun bind(holder: UserViewHolder, user: User, userClickListener: OnUserClickListener) {
        holder.setAvatar(user.avatar)
        holder.setName(user.userName)
        holder.setEmail(user.email)

        when (user.status) {
            holder.itemView.context.getString(R.string.user_active_status) -> holder.setStatus(
                ResourcesCompat.getDrawable(
                    holder.itemView.resources, R.drawable.ic_status_active, null
                )
            )
            holder.itemView.context.getString(R.string.user_idle_status) -> holder.setStatus(
                ResourcesCompat.getDrawable(
                    holder.itemView.resources, R.drawable.ic_status_idle, null
                )
            )
            holder.itemView.context.getString(R.string.user_offline_status) -> holder.hideStatus()
        }

        holder.itemView.setOnClickListener {
            userClickListener.userClicked(user)
        }
    }

}