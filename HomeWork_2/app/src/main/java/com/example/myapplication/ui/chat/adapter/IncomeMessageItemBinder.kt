package com.example.myapplication.ui.chat.adapter

import com.example.myapplication.domain.chat.entity.IncomeMessage
import com.example.myapplication.ui.chat.adapter.viewholder.IncomeMessageViewHolder
import com.example.myapplication.ui.chat.listener.OnMessageClickListener

object IncomeMessageItemBinder {

    fun bind(
        holder: IncomeMessageViewHolder,
        message: IncomeMessage,
        position: Int,
        messageClickListener: OnMessageClickListener
    ) {
        holder.setSenderName(message.user.userName)
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