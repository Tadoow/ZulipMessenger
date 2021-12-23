package com.example.myapplication.presentation.chat.adapter

import com.example.myapplication.domain.entity.chat.OutcomeMessage
import com.example.myapplication.presentation.chat.adapter.viewholder.OutcomeMessageViewHolder
import com.example.myapplication.presentation.chat.listener.OnMessageClickListener

object OutcomeMessageItemBinder {

    fun bind(
        holder: OutcomeMessageViewHolder,
        message: OutcomeMessage,
        messagePosition: Int,
        messageClickListener: OnMessageClickListener
    ) {
        holder.setMessageText(message.messageText)

        holder.cleanFlexBoxLayout()
        if (message.emojiList.isNotEmpty()) {
            holder.addReactionAppendView(messagePosition)
        }
        for (emoji in message.emojiList) {
            holder.addReactionView(emoji, messagePosition)
        }

        holder.setOnClickListener { messageClickListener.messageLongClicked(messagePosition) }
    }

}