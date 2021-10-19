package com.example.myapplication.view.viewholders

import android.util.Log
import android.view.View
import android.widget.TextView
import com.example.myapplication.R
import com.example.myapplication.model.DateMessage
import com.example.myapplication.model.IncomeMessage

class DateMessageViewHolder(itemView: View) : MyViewHolder(itemView) {
    private var dateMessageTextView: TextView? = null

    init {
        dateMessageTextView = itemView.findViewById(R.id.dateMessageTextView)
    }

    fun setData(data: DateMessage) {
        dateMessageTextView?.text = data.toString()
    }
}