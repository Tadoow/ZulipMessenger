package com.example.myapplication.ui.chat.adapter.viewholder

import android.view.View
import androidx.core.view.isVisible
import com.example.myapplication.databinding.OutcomeMessageBinding
import com.example.myapplication.domain.chat.entity.Reaction
import com.example.myapplication.ui.chat.listener.OnReactionClickListener

class OutcomeMessageViewHolder(
    itemView: View,
    private val reactionClickListener: OnReactionClickListener
) : ChatItemViewHolder(itemView) {

    private val binding = OutcomeMessageBinding.bind(itemView)

    fun setMessageText(messageText: String) {
        binding.messageTextView.text = messageText
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
        binding.messageTextView.setOnLongClickListener(messageClickListener)
    }
}