package com.example.myapplication.presentation.view.viewholders

import android.view.View
import android.widget.LinearLayout
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.size
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.presentation.models.ChatItem
import com.example.myapplication.presentation.models.Reaction
import com.example.myapplication.presentation.view.customViewGroups.FlexBoxLayout
import com.example.myapplication.presentation.view.customViews.ReactionView
import com.example.myapplication.presentation.view.listeners.OnReactionClickListener

abstract class ChatItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun setData(data: ChatItem, position: Int)

    fun cleanFlexBoxLayout(flexBoxLayout: FlexBoxLayout?) {
        val childCount = flexBoxLayout?.childCount ?: 0
        for (i in 0 until childCount - 1) {
            val child = flexBoxLayout?.getChildAt(0)
            flexBoxLayout?.removeView(child)
        }
    }

    fun addReactionView(
        reaction: Reaction,
        flexBoxLayout: FlexBoxLayout?,
        reactionClickListener: OnReactionClickListener,
        position: Int
    ) {
        if (reaction.getClicksCount() != 0) {
            val layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            layoutParams.setMargins(0, 21, 30, 0)
            val smileView = ReactionView(itemView.context).apply {
                reactionUnicode = reaction.emoji
                reactionCount = reaction.getClicksCount()
                isSelected = reaction.reactionClickState
                background =
                    ResourcesCompat.getDrawable(resources, R.drawable.reaction_view_states, null)
                setPadding(27, 15, 27, 21)
            }
            flexBoxLayout?.addView(smileView, flexBoxLayout.size - 1, layoutParams)
            smileView.setOnClickListener {
                reactionClickListener.reactionInMessageClicked(reaction, position)
            }
        }
    }
}