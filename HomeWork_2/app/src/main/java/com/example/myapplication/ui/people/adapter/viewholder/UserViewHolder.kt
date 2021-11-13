package com.example.myapplication.ui.people.adapter.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.UserItemBinding

class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val binding = UserItemBinding.bind(itemView)

    fun setUserName(userName: String) {
        binding.userName.text = userName
    }

    fun setUserEmail(userEmail: String) {
        binding.userEmail.text = userEmail
    }
}