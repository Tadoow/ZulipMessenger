package com.example.myapplication.view.viewholders

import android.view.View
import android.widget.TextView
import com.example.myapplication.R
import com.example.myapplication.model.DateMessage

class DateMessageViewHolder(itemView: View) : MessageViewHolder(itemView) {
    private var dateMessageTextView: TextView? = null

    init {
        dateMessageTextView = itemView.findViewById(R.id.dateMessageTextView)
    }

    fun setData(data: DateMessage) {
        dateMessageTextView?.text = data.toString()
    }
}