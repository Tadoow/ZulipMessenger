package com.example.myapplication.ui.people.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.domain.people.entity.User
import com.example.myapplication.ui.people.adapter.viewholder.UserViewHolder
import com.example.myapplication.ui.people.listener.OnUserClickListener

class UsersAdapter(
    private var users: List<User> = emptyList(),
    private val userClickListener: OnUserClickListener
) : RecyclerView.Adapter<UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.user_item, parent, false)
        return UserViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = users[position]
        UserItemBinder.bind(holder, user, userClickListener)
    }

    override fun getItemCount(): Int {
        return users.size
    }

    fun setItems(users: List<User>) {
        this.users = users
        notifyDataSetChanged()
    }
}