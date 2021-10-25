package com.example.myapplication.view.viewholders

import android.view.View
import android.widget.TextView
import com.example.myapplication.R
import com.example.myapplication.models.Message

class DateMessageViewHolder(itemView: View) : MessageViewHolder(itemView) {
    private var dateMessageTextView: TextView? = null

    init {
        dateMessageTextView = itemView.findViewById(R.id.dateMessageTextView)
    }

    override fun setData(data: Message, position: Int) {
        dateMessageTextView?.text = data.toString()
    }
}