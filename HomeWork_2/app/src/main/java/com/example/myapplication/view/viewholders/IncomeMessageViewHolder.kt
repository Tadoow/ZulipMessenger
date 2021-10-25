package com.example.myapplication.view.viewholders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.example.myapplication.R
import com.example.myapplication.models.IncomeMessage
import com.example.myapplication.models.Message
import com.example.myapplication.view.OnReactionClickListener
import com.example.myapplication.view.customViewGroups.FlexBoxLayout

class IncomeMessageViewHolder(
    itemView: View,
    private val reactionClickListener: OnReactionClickListener
) : MessageViewHolder(itemView) {

    private var senderTextView: TextView? = null
    private var senderMessageTextView: TextView? = null
    private var addReactionImageView: ImageView? = null
    private var flexBoxLayout: FlexBoxLayout? = null

    init {
        senderTextView = itemView.findViewById(R.id.senderTextView)
        senderMessageTextView = itemView.findViewById(R.id.senderMessageTextView)
        addReactionImageView = itemView.findViewById(R.id.reactionAddView)
        flexBoxLayout = itemView.findViewById(R.id.flexBoxLayout)
    }

    fun setOnClickListener(
        addReactionClickListener: View.OnClickListener?,
        messageClickListener: View.OnLongClickListener?
    ) {
        addReactionImageView?.setOnClickListener(addReactionClickListener)
        senderMessageTextView?.setOnLongClickListener(messageClickListener)
    }

    override fun setData(data: Message, position: Int) {
        senderTextView?.text = (data as IncomeMessage).senderName
        senderMessageTextView?.text = data.messageText
        addReactionImageView?.visibility = if (data.reactions.isEmpty()) View.GONE else View.VISIBLE
        cleanFlexBoxLayout(flexBoxLayout)
        for (reaction in data.reactions) {
            addReactionView(reaction, flexBoxLayout, reactionClickListener, position)
        }
    }
}