package com.example.myapplication.ui.chat.adapter

import com.example.myapplication.domain.chat.entity.OutcomeMessage
import com.example.myapplication.ui.chat.adapter.viewholder.OutcomeMessageViewHolder
import com.example.myapplication.ui.chat.listener.OnMessageClickListener

object OutcomeMessageItemBinder {

    fun bind(
        holder: OutcomeMessageViewHolder,
        message: OutcomeMessage,
        position: Int,
        messageClickListener: OnMessageClickListener
    ) {
        holder.setMessageText(message.messageText)
        holder.setAddReactionViewVisibility(message.reactions.isEmpty())

        holder.cleanFlexBoxLayout()
        for (reaction in message.reactions) {
            holder.addReactionView(reaction, position)
        }

        holder.setOnClickListener(
            { messageClickListener.addEmojiClicked(position) },
            { messageClickListener.messageLongClicked(position) }
        )
    }
}