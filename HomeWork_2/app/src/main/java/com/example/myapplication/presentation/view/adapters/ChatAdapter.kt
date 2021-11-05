package com.example.myapplication.presentation.view.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.presentation.models.ChatItem
import com.example.myapplication.presentation.models.Date
import com.example.myapplication.presentation.models.MessageTypes
import com.example.myapplication.presentation.view.listeners.OnMessageClickListener
import com.example.myapplication.presentation.view.listeners.OnReactionClickListener
import com.example.myapplication.presentation.view.viewholders.ChatItemViewHolder
import com.example.myapplication.presentation.view.viewholders.DateViewHolder
import com.example.myapplication.presentation.view.viewholders.IncomeMessageViewHolder
import com.example.myapplication.presentation.view.viewholders.OutcomeMessageViewHolder

class ChatAdapter(
    private var chatItems: List<ChatItem>,
    private val messageClickListener: OnMessageClickListener,
    private val reactionClickListener: OnReactionClickListener
) :
    RecyclerView.Adapter<ChatItemViewHolder>() {

    override fun getItemViewType(position: Int): Int {
        return chatItems[position].getType()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatItemViewHolder {
        Log.d("TAG", "onCreateViewHolder() called with: parent = $parent, viewType = $viewType")
        when (viewType) {
            MessageTypes.INCOME.ordinal -> {
                val itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.income_message, parent, false)
                return IncomeMessageViewHolder(itemView, reactionClickListener)
            }
            MessageTypes.OUTCOME.ordinal -> {
                val itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.outcome_message, parent, false)
                return OutcomeMessageViewHolder(itemView, reactionClickListener)
            }
            Date.DATE -> {
                val itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.date_item, parent, false)
                return DateViewHolder(itemView)
            }
            else -> throw IllegalArgumentException("Such viewType not found: $viewType")
        }
    }

    override fun onBindViewHolder(holder: ChatItemViewHolder, position: Int) {
        Log.d("TAG", "onBindViewHolder() called with: holder = $holder, position = $position")
        val message = chatItems[position]
        when (holder) {
            is IncomeMessageViewHolder -> {
                holder.setData(message, position)
                holder.setOnClickListener(
                    { messageClickListener.addEmojiClicked(position) },
                    { messageClickListener.messageLongClicked(position) }
                )
            }
            is OutcomeMessageViewHolder -> {
                holder.setData(message, position)
                holder.setOnClickListener(
                    { messageClickListener.addEmojiClicked(position) },
                    { messageClickListener.messageLongClicked(position) }
                )
            }
            is DateViewHolder -> holder.setData(message, position)
            else -> throw IllegalArgumentException("Such holder not found: $holder")
        }
    }

    override fun getItemCount() = chatItems.size

    fun addItem(newChatItem: ChatItem) {
        (chatItems as ArrayList).add(newChatItem)
        notifyItemInserted(chatItems.size - 1)
    }
}