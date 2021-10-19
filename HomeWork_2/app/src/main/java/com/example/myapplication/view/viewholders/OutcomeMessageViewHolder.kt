package com.example.myapplication.view.viewholders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.example.myapplication.R
import com.example.myapplication.model.OutcomeMessage
import com.example.myapplication.view.customViewGroup.FlexBoxLayout

class OutcomeMessageViewHolder(itemView: View) : MyViewHolder(itemView) {
    private var messageTextView: TextView? = null
    private var customAddView: ImageView? = null
    private var flexBoxLayout: FlexBoxLayout? = null

    init {
        messageTextView = itemView.findViewById(R.id.messageTextView)
        customAddView = itemView.findViewById(R.id.customSmileAdd)
        flexBoxLayout = itemView.findViewById(R.id.flexBoxLayout)
    }

    fun setOnClickListener(viewClickListener: View.OnClickListener?) {
        customAddView?.setOnClickListener(viewClickListener)
    }

    fun getFlexBoxLayout(): FlexBoxLayout? {
        return flexBoxLayout
    }

    fun setData(data: OutcomeMessage) {
        messageTextView?.text = data.messageText
    }
}