package com.example.myapplication.presentation.view.viewholders

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.presentation.models.User

class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val userNameTextView: TextView
    private val userEmailTextView: TextView

    init {
        userNameTextView = itemView.findViewById(R.id.user_name)
        userEmailTextView = itemView.findViewById(R.id.user_email)
    }

    fun setData(user: User) {
        userNameTextView.text = user.userName
        userEmailTextView.text = user.email
    }
}