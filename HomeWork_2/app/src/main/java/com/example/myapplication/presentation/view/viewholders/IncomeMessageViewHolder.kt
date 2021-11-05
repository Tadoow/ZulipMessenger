package com.example.myapplication.presentation.view.viewholders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.example.myapplication.R
import com.example.myapplication.presentation.models.ChatItem
import com.example.myapplication.presentation.models.IncomeMessage
import com.example.myapplication.presentation.view.customViewGroups.FlexBoxLayout
import com.example.myapplication.presentation.view.listeners.OnReactionClickListener

class IncomeMessageViewHolder(
    itemView: View,
    private val reactionClickListener: OnReactionClickListener
) : ChatItemViewHolder(itemView) {

    private val senderTextView: TextView
    private val senderMessageTextView: TextView
    private val addReactionImageView: ImageView
    private val flexBoxLayout: FlexBoxLayout

    init {
        senderTextView = itemView.findViewById(R.id.sender_text_view)
        senderMessageTextView = itemView.findViewById(R.id.sender_message_text_view)
        addReactionImageView = itemView.findViewById(R.id.reaction_add_view)
        flexBoxLayout = itemView.findViewById(R.id.flexBoxLayout)
    }

    fun setOnClickListener(
        addReactionClickListener: View.OnClickListener?,
        messageClickListener: View.OnLongClickListener?
    ) {
        addReactionImageView.setOnClickListener(addReactionClickListener)
        senderMessageTextView.setOnLongClickListener(messageClickListener)
    }

    override fun setData(data: ChatItem, position: Int) {
        senderTextView.text = (data as IncomeMessage).user.userName
        senderMessageTextView.text = data.messageText
        addReactionImageView.visibility = if (data.reactions.isEmpty()) View.GONE else View.VISIBLE
        cleanFlexBoxLayout(flexBoxLayout)
        for (reaction in data.reactions) {
            addReactionView(reaction, flexBoxLayout, reactionClickListener, position)
        }
    }
}