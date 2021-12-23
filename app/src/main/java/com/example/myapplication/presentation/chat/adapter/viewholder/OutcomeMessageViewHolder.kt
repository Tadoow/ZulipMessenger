package com.example.myapplication.presentation.chat.adapter.viewholder

import android.view.View
import com.example.myapplication.databinding.OutcomeMessageBinding
import com.example.myapplication.domain.entity.chat.Reaction
import com.example.myapplication.presentation.chat.listener.OnReactionClickListener

class OutcomeMessageViewHolder(
    itemView: View,
    private val reactionClickListener: OnReactionClickListener
) : ChatItemViewHolder(itemView) {

    private val binding = OutcomeMessageBinding.bind(itemView)

    fun setMessageText(messageText: String) {
        binding.messageTextView.text = messageText
    }

    fun addReactionAppendView(messagePosition: Int) {
        addReactionAppendView(
            flexBoxLayout = binding.flexBoxLayout,
            reactionClickListener = reactionClickListener,
            messagePosition = messagePosition
        )
    }

    fun addReactionView(emoji: Reaction, messagePosition: Int) {
        addReactionView(
            emoji = emoji,
            flexBoxLayout = binding.flexBoxLayout,
            reactionClickListener = reactionClickListener,
            messagePosition = messagePosition
        )
    }

    fun cleanFlexBoxLayout() {
        cleanFlexBoxLayout(binding.flexBoxLayout)
    }

    fun setOnClickListener(messageClickListener: View.OnLongClickListener?) {
        binding.messageTextView.setOnLongClickListener(messageClickListener)
    }

}