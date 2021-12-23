package com.example.myapplication.presentation.chat.adapter

import com.example.myapplication.domain.entity.chat.IncomeMessage
import com.example.myapplication.presentation.chat.adapter.viewholder.IncomeMessageViewHolder
import com.example.myapplication.presentation.chat.listener.OnMessageClickListener

object IncomeMessageItemBinder {

    fun bind(
        holder: IncomeMessageViewHolder,
        message: IncomeMessage,
        messagePosition: Int,
        messageClickListener: OnMessageClickListener
    ) {
        holder.setSenderName(message.user.userName)
        holder.setAvatar(message.user.avatar)
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