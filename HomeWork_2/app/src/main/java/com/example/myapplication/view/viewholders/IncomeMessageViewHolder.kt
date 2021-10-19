package com.example.myapplication.view.viewholders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.example.myapplication.R
import com.example.myapplication.model.IncomeMessage
import com.example.myapplication.view.customViewGroup.FlexBoxLayout

class IncomeMessageViewHolder(itemView: View) : MyViewHolder(itemView) {
    private var senderTextView: TextView? = null
    private var senderMessageTextView: TextView? = null
    private var customAddView: ImageView? = null
    private var flexBoxLayout: FlexBoxLayout? = null

    init {
        senderTextView = itemView.findViewById(R.id.senderTextView)
        senderMessageTextView = itemView.findViewById(R.id.senderMessageTextView)
        customAddView = itemView.findViewById(R.id.customSmileAdd)
        flexBoxLayout = itemView.findViewById(R.id.flexBoxLayout)
    }

    fun setOnClickListener(addViewClickListener: View.OnClickListener?, messageClickListener: View.OnClickListener?) {
        customAddView?.setOnClickListener(addViewClickListener)
        senderMessageTextView?.setOnClickListener(messageClickListener)
    }

    fun getFlexBoxLayout(): FlexBoxLayout? {
        return flexBoxLayout
    }

    fun setData(data: IncomeMessage) {
        senderMessageTextView?.text = data.messageText
    }
}