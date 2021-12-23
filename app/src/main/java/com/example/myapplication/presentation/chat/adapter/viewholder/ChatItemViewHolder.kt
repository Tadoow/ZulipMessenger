package com.example.myapplication.presentation.chat.adapter.viewholder

import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.size
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.domain.entity.chat.Reaction
import com.example.myapplication.presentation.chat.customView.ReactionView
import com.example.myapplication.presentation.chat.customViewGroup.FlexBoxLayout
import com.example.myapplication.presentation.chat.listener.OnReactionClickListener

abstract class ChatItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun cleanFlexBoxLayout(layout: FlexBoxLayout?) {
        val childCount = layout?.childCount ?: 0
        for (i in 0 until childCount) {
            val child = layout?.getChildAt(0)
            layout?.removeView(child)
        }
    }

    fun addReactionView(
        emoji: Reaction,
        flexBoxLayout: FlexBoxLayout?,
        reactionClickListener: OnReactionClickListener,
        messagePosition: Int
    ) {
        if (emoji.getReactionsCount() != 0) {
            val layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            layoutParams.setMargins(0, 21, 30, 0)
            val emojiReactionView = createReactionView(emoji)
            flexBoxLayout?.addView(emojiReactionView, flexBoxLayout.size - 1, layoutParams)
            emojiReactionView.setOnClickListener {
                reactionClickListener.reactionInMessageClicked(emoji, messagePosition)
            }
        }
    }

    private fun createReactionView(emoji: Reaction): ReactionView {
        return ReactionView(itemView.context).apply {
            emojiCode = convertEmojiCode(emoji.emojiCode)
            reactionsCount = emoji.getReactionsCount()
            isSelected = emoji.reactionClickState
            background =
                ResourcesCompat.getDrawable(resources, R.drawable.reaction_view_states, null)
            setPadding(27, 15, 27, 21)
        }
    }

    private fun convertEmojiCode(emojiCode: String): String {
        if (emojiCode.contains("zulip")) return "Z"
        val separator = emojiCode.indexOf("-")
        return if (separator == -1) {
            String(Character.toChars(emojiCode.toInt(16)))
        } else {
            String(Character.toChars(emojiCode.substring(0, separator).toInt(16))) +
                    String(Character.toChars(emojiCode.substring(separator + 1).toInt(16)))
        }
    }

    fun addReactionAppendView(
        flexBoxLayout: FlexBoxLayout?,
        reactionClickListener: OnReactionClickListener,
        messagePosition: Int
    ) {
        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        layoutParams.setMargins(0, 21, 0, 0)
        val emojiReactionAppendView = ImageView(itemView.context).apply {
            background = ResourcesCompat.getDrawable(resources, R.drawable.add_reaction_view, null)
            isClickable = true
        }

        flexBoxLayout?.addView(emojiReactionAppendView, flexBoxLayout.size - 1, layoutParams)
        emojiReactionAppendView.setOnClickListener {
            reactionClickListener.reactionAppendClicked(messagePosition)
        }
    }

}