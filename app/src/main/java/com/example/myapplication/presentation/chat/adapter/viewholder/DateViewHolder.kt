package com.example.myapplication.presentation.chat.adapter.viewholder

import android.view.View
import com.example.myapplication.databinding.DateItemBinding

class DateViewHolder(itemView: View) : ChatItemViewHolder(itemView) {

    private val binding = DateItemBinding.bind(itemView)

    fun setDate(date: String) {
        binding.dateItemTextView.text = date
    }

}