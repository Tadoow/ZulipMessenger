package com.example.myapplication.presentation.view.viewholders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.example.myapplication.R
import com.example.myapplication.presentation.models.ChatItem
import com.example.myapplication.presentation.models.OutcomeMessage
import com.example.myapplication.presentation.view.customViewGroups.FlexBoxLayout
import com.example.myapplication.presentation.view.listeners.OnReactionClickListener

class OutcomeMessageViewHolder(
    itemView: View,
    private val reactionClickListener: OnReactionClickListener
) : ChatItemViewHolder(itemView) {

    private val messageTextView: TextView
    private val addReactionImageView: ImageView
    private val flexBoxLayout: FlexBoxLayout

    init {
        messageTextView = itemView.findViewById(R.id.message_text_view)
        addReactionImageView = itemView.findViewById(R.id.reaction_add_view)
        flexBoxLayout = itemView.findViewById(R.id.flexBoxLayout)
    }

    fun setOnClickListener(
        addReactionClickListener: View.OnClickListener?,
        messageClickListener: View.OnLongClickListener?
    ) {
        addReactionImageView.setOnClickListener(addReactionClickListener)
        messageTextView.setOnLongClickListener(messageClickListener)
    }

    override fun setData(data: ChatItem, position: Int) {
        messageTextView.text = (data as OutcomeMessage).messageText
        addReactionImageView.visibility = if (data.reactions.isEmpty()) View.GONE else View.VISIBLE
        cleanFlexBoxLayout(flexBoxLayout)
        for (reaction in data.reactions) {
            addReactionView(reaction, flexBoxLayout, reactionClickListener, position)
        }
    }
}