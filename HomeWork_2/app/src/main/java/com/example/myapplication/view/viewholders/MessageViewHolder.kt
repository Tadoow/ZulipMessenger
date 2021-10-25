package com.example.myapplication.view.viewholders

import android.util.Log
import android.view.View
import android.widget.LinearLayout
import androidx.core.view.size
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.models.Message
import com.example.myapplication.models.Reaction
import com.example.myapplication.view.OnReactionClickListener
import com.example.myapplication.view.customViewGroups.FlexBoxLayout
import com.example.myapplication.view.customViews.ReactionView

abstract class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun setData(data: Message, position: Int)

    fun cleanFlexBoxLayout(flexBoxLayout: FlexBoxLayout?) {
        val childCount = flexBoxLayout?.childCount ?: 0
        Log.d("TAG", "cleanFlexBoxLayout() called childCount ${flexBoxLayout?.childCount}")
        for (i in 0 until childCount - 1) {
            val child = flexBoxLayout?.getChildAt(0)
            Log.d("TAG", "cleanFlexBoxLayout() child $child")
            flexBoxLayout?.removeView(child)
        }
        Log.d("TAG", "cleanFlexBoxLayout() called childCount ${flexBoxLayout?.childCount}")
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
            layoutParams.setMargins(0, 10, 30, 10)
            val smileView = ReactionView(itemView.context).apply {
                smileUnicode = reaction.emoji
                smileCount = reaction.getClicksCount()
                isSelected = reaction.reactionClickState
                background = resources.getDrawable(R.drawable.reaction_view_states, null)
                setPadding(35, 25, 35, 25)
            }
            flexBoxLayout?.addView(smileView, flexBoxLayout.size - 1, layoutParams)
            smileView.setOnClickListener {
                reactionClickListener.reactionClicked(reaction, position)
            }
        }
    }
}