package com.example.myapplication.presentation.chat.adapter.viewholder

import android.view.View
import com.bumptech.glide.Glide
import com.example.myapplication.databinding.MessageViewGroupBinding
import com.example.myapplication.domain.entity.chat.Reaction
import com.example.myapplication.presentation.chat.listener.OnReactionClickListener

class IncomeMessageViewHolder(
    itemView: View,
    private val reactionClickListener: OnReactionClickListener
) : ChatItemViewHolder(itemView) {

    private val binding = MessageViewGroupBinding.bind(itemView)

    fun setSenderName(senderName: String) {
        binding.senderTextView.text = senderName
    }

    fun setAvatar(url: String) {
        Glide.with(itemView)
            .load(url)
            .into(binding.userAvatarInProfile)
    }

    fun setMessageText(messageText: String) {
        binding.senderMessageTextView.text = messageText
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
        binding.messageLayout.setOnLongClickListener(messageClickListener)
    }

}