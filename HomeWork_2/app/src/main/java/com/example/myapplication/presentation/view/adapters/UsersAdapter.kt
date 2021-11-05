package com.example.myapplication.presentation.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.presentation.models.User
import com.example.myapplication.presentation.view.listeners.OnUserClickListener
import com.example.myapplication.presentation.view.viewholders.UserViewHolder

class UsersAdapter(
    private val users: List<User>,
    private val userClickListener: OnUserClickListener
) : RecyclerView.Adapter<UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.user_item, parent, false)
        return UserViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = users[position]
        holder.setData(user)
        holder.itemView.setOnClickListener {
            userClickListener.userClicked(user)
        }
    }

    override fun getItemCount(): Int {
        return users.size
    }

}