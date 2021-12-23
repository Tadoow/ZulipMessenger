package com.example.myapplication.presentation.people.adapter.viewholder

import android.graphics.drawable.Drawable
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.databinding.UserItemBinding

class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val binding = UserItemBinding.bind(itemView)

    fun setName(userName: String) {
        binding.userName.text = userName
    }

    fun setEmail(userEmail: String) {
        binding.userEmail.text = userEmail
    }

    fun setStatus(drawable: Drawable?) {
        binding.userStatus.visibility = View.VISIBLE
        binding.userStatus.setImageDrawable(drawable)
    }

    fun hideStatus() {
        binding.userStatus.visibility = View.GONE
    }

    fun setAvatar(url: String) {
        Glide.with(itemView)
            .load(url)
            .into(binding.userAvatar)
    }

}