package com.example.myapplication.ui.chat.adapter.viewholder

import android.view.View
import androidx.core.view.isVisible
import com.example.myapplication.databinding.MessageViewGroupBinding
import com.example.myapplication.domain.chat.entity.Reaction
import com.example.myapplication.ui.chat.listener.OnReactionClickListener

class IncomeMessageViewHolder(
    itemView: View,
    private val reactionClickListener: OnReactionClickListener
) : ChatItemViewHolder(itemView) {

    private val binding = MessageViewGroupBinding.bind(itemView)

    fun setSenderName(senderName: String) {
        binding.senderTextView.text = senderName
    }

    fun setMessageText(messageText: String) {
        binding.senderMessageTextView.text = messageText
    }

    fun setAddReactionViewVisibility(visibility: Boolean) {
        binding.reactionAddView.isVisible = visibility
    }

    fun addReactionView(reaction: Reaction, messagePosition: Int) {
        addReactionView(reaction, binding.flexBoxLayout, reactionClickListener, messagePosition)
    }

    fun cleanFlexBoxLayout() {
        cleanFlexBoxLayout(binding.flexBoxLayout)
    }

    fun setOnClickListener(
        addReactionClickListener: View.OnClickListener?,
        messageClickListener: View.OnLongClickListener?
    ) {
        binding.reactionAddView.setOnClickListener(addReactionClickListener)
        binding.senderMessageTextView.setOnLongClickListener(messageClickListener)
    }
}