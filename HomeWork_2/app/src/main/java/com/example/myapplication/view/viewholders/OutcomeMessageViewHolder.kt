package com.example.myapplication.view.viewholders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.example.myapplication.R
import com.example.myapplication.models.Message
import com.example.myapplication.models.OutcomeMessage
import com.example.myapplication.view.OnReactionClickListener
import com.example.myapplication.view.customViewGroups.FlexBoxLayout

class OutcomeMessageViewHolder(
    itemView: View,
    private val reactionClickListener: OnReactionClickListener
) : MessageViewHolder(itemView) {

    private var messageTextView: TextView? = null
    private var addReactionImageView: ImageView? = null
    private var flexBoxLayout: FlexBoxLayout? = null

    init {
        messageTextView = itemView.findViewById(R.id.messageTextView)
        addReactionImageView = itemView.findViewById(R.id.reactionAddView)
        flexBoxLayout = itemView.findViewById(R.id.flexBoxLayout)
    }

    fun setOnClickListener(
        addReactionClickListener: View.OnClickListener?,
        messageClickListener: View.OnLongClickListener?
    ) {
        addReactionImageView?.setOnClickListener(addReactionClickListener)
        messageTextView?.setOnLongClickListener(messageClickListener)
    }

    override fun setData(data: Message, position: Int) {
        messageTextView?.text = (data as OutcomeMessage).messageText
        addReactionImageView?.visibility = if (data.reactions.isEmpty()) View.GONE else View.VISIBLE
        cleanFlexBoxLayout(flexBoxLayout)
        for (reaction in data.reactions) {
            addReactionView(reaction, flexBoxLayout, reactionClickListener, position)
        }
    }
}