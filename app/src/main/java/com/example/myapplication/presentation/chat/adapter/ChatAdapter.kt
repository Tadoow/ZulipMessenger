package com.example.myapplication.presentation.chat.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.domain.entity.chat.ChatItem
import com.example.myapplication.domain.entity.chat.Date
import com.example.myapplication.domain.entity.chat.IncomeMessage
import com.example.myapplication.domain.entity.chat.OutcomeMessage
import com.example.myapplication.presentation.chat.adapter.viewholder.ChatItemViewHolder
import com.example.myapplication.presentation.chat.adapter.viewholder.DateViewHolder
import com.example.myapplication.presentation.chat.adapter.viewholder.IncomeMessageViewHolder
import com.example.myapplication.presentation.chat.adapter.viewholder.OutcomeMessageViewHolder
import com.example.myapplication.presentation.chat.listener.OnMessageClickListener
import com.example.myapplication.presentation.chat.listener.OnReactionClickListener

class ChatAdapter(
    var chatItems: List<ChatItem> = emptyList(),
    private val messageClickListener: OnMessageClickListener,
    private val reactionClickListener: OnReactionClickListener
) :
    RecyclerView.Adapter<ChatItemViewHolder>() {

    override fun getItemViewType(position: Int) = when (chatItems[position]) {
        is IncomeMessage -> R.layout.income_message
        is OutcomeMessage -> R.layout.outcome_message
        is Date -> R.layout.date_item
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatItemViewHolder {
        Log.d("TAG", "onCreateViewHolder() called with: parent = $parent, viewType = $viewType")
        val itemView = LayoutInflater.from(parent.context)
            .inflate(viewType, parent, false)
        return when (viewType) {
            R.layout.income_message -> IncomeMessageViewHolder(itemView, reactionClickListener)
            R.layout.outcome_message -> OutcomeMessageViewHolder(
                itemView,
                reactionClickListener
            )
            R.layout.date_item -> DateViewHolder(itemView)
            else -> throw IllegalArgumentException("Such viewType not found: $viewType")
        }
    }

    override fun onBindViewHolder(holder: ChatItemViewHolder, position: Int) {
        Log.d("TAG", "onBindViewHolder() called with: holder = $holder, position = $position")
        val message = chatItems[position]
        when (holder) {
            is IncomeMessageViewHolder -> IncomeMessageItemBinder.bind(
                holder, message as IncomeMessage, position, messageClickListener
            )
            is OutcomeMessageViewHolder -> OutcomeMessageItemBinder.bind(
                holder, message as OutcomeMessage, position, messageClickListener
            )
            is DateViewHolder -> holder.setDate(message.toString())
            else -> throw IllegalArgumentException("Such holder not found: $holder")
        }
    }

    override fun getItemCount(): Int {
        Log.d("CHAT", "getItemCount: ${chatItems.size}")
        return chatItems.size
    }

    fun addItem(item: ChatItem) {
        (chatItems as ArrayList).add(item)
        notifyItemInserted(chatItems.size - 1)
    }

    fun addItems(items: List<ChatItem>) {
        (chatItems as ArrayList).addAll(items)
        notifyItemRangeInserted(chatItems.size - 1, items.size)
    }

    fun setItems(items: List<ChatItem>) {
        this.chatItems = items
        notifyDataSetChanged()
    }

}