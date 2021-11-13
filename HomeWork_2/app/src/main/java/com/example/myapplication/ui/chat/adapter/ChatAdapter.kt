package com.example.myapplication.ui.chat.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.domain.chat.entity.*
import com.example.myapplication.ui.chat.adapter.viewholder.ChatItemViewHolder
import com.example.myapplication.ui.chat.adapter.viewholder.DateViewHolder
import com.example.myapplication.ui.chat.adapter.viewholder.IncomeMessageViewHolder
import com.example.myapplication.ui.chat.adapter.viewholder.OutcomeMessageViewHolder
import com.example.myapplication.ui.chat.listener.OnMessageClickListener
import com.example.myapplication.ui.chat.listener.OnReactionClickListener

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
            is IncomeMessageViewHolder ->
                IncomeMessageItemBinder.bind(
                    holder, message as IncomeMessage, position, messageClickListener
                )
            is OutcomeMessageViewHolder ->
                OutcomeMessageItemBinder.bind(
                    holder, message as OutcomeMessage, position, messageClickListener
                )
            is DateViewHolder -> holder.setDate(message.toString())
            else -> throw IllegalArgumentException("Such holder not found: $holder")
        }
    }

    override fun getItemCount() = chatItems.size

    fun addItem(chatItem: ChatItem) {
        (chatItems as ArrayList).add(chatItem)
        notifyItemInserted(chatItems.size - 1)
    }
}