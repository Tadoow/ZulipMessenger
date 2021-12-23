package com.example.myapplication.presentation.people.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.domain.entity.people.User
import com.example.myapplication.presentation.people.adapter.viewholder.UserViewHolder
import com.example.myapplication.presentation.people.listener.OnUserClickListener

class UsersAdapter(
    private var users: List<User> = arrayListOf(),
    private val userClickListener: OnUserClickListener
) : RecyclerView.Adapter<UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        Log.d("TAG", "onCreateViewHolder() called with: parent = $parent, viewType = $viewType")
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.user_item, parent, false)
        return UserViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        Log.d("PeopleAdapter", "onBindViewHolder: ")
        val user = users[position]
        UserItemBinder.bind(holder, user, userClickListener)
    }

    override fun getItemCount(): Int {
        Log.d("USERS", "getItemCount: ${users.size}")
        return users.size
    }

    fun setItems(users: List<User>) {
        this.users = users
        notifyDataSetChanged()
    }

    fun updateUserStatus(userId: Int, status: String) {
        val user = (users as ArrayList).find { it.userId == userId }
        val userIndex = users.indexOf(user)
        users[userIndex].status = status
        notifyItemChanged(userIndex)
    }
}