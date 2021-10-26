package com.example.myapplication.view.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.models.Message
import com.example.myapplication.models.MessageTypes
import com.example.myapplication.view.OnMessageClickListener
import com.example.myapplication.view.OnReactionClickListener
import com.example.myapplication.view.viewholders.DateMessageViewHolder
import com.example.myapplication.view.viewholders.IncomeMessageViewHolder
import com.example.myapplication.view.viewholders.MessageViewHolder
import com.example.myapplication.view.viewholders.OutcomeMessageViewHolder

class MessageAdapter(
    private var messages: List<Message>,
    private val messageClickListener: OnMessageClickListener,
    private val reactionClickListener: OnReactionClickListener
) :
    RecyclerView.Adapter<MessageViewHolder>() {

    override fun getItemViewType(position: Int): Int {
        return messages[position].getType()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
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
            MessageTypes.DATE.ordinal -> {
                val itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.date_message, parent, false)
                return DateMessageViewHolder(itemView)
            }
            else -> throw IllegalArgumentException("Illegal type $viewType")
        }
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        Log.d("TAG", "onBindViewHolder() called with: holder = $holder, position = $position")
        val message = messages[position]
        when (holder) {
            is IncomeMessageViewHolder -> {
                holder.setData(message, position)
                holder.setOnClickListener(
                    { messageClickListener.addEmojiViewClicked(position) },
                    { messageClickListener.messageViewClicked(position) }
                )
            }
            is OutcomeMessageViewHolder -> {
                holder.setData(message, position)
                holder.setOnClickListener(
                    { messageClickListener.addEmojiViewClicked(position) },
                    { messageClickListener.messageViewClicked(position) }
                )
            }
            is DateMessageViewHolder -> holder.setData(message, position)
        }
    }

    override fun getItemCount() = messages.size

    fun addItem(newMessage: Message) {
        (messages as MutableList).add(newMessage)
        notifyItemInserted(messages.size - 1)
    }
}