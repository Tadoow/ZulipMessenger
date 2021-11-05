package com.example.myapplication.presentation.view.viewholders

import android.view.View
import android.widget.TextView
import com.example.myapplication.R
import com.example.myapplication.presentation.models.ChatItem

class DateViewHolder(itemView: View) : ChatItemViewHolder(itemView) {

    private val dateTextView: TextView

    init {
        dateTextView = itemView.findViewById(R.id.date_item_text_view)
    }

    override fun setData(data: ChatItem, position: Int) {
        dateTextView.text = data.toString()
    }
}