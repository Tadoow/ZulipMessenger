package com.example.myapplication.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.model.*
import com.example.myapplication.view.OnAddSmileViewListener
import com.example.myapplication.view.viewholders.DateMessageViewHolder
import com.example.myapplication.view.viewholders.IncomeMessageViewHolder
import com.example.myapplication.view.viewholders.MyViewHolder
import com.example.myapplication.view.viewholders.OutcomeMessageViewHolder

class MyAdapter(private var messages: List<Message>, private val listener: OnAddSmileViewListener) :
    RecyclerView.Adapter<MyViewHolder>() {

    override fun getItemViewType(position: Int): Int {
        return messages[position].getType()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        when (viewType) {
            MessageTypes.INCOME.ordinal -> {
                val itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.income_message_layout, parent, false)
                IncomeMessageViewHolder(itemView)
            }
            MessageTypes.OUTCOME.ordinal -> {
                val itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.outcome_message_layout, parent, false)
                OutcomeMessageViewHolder(itemView)
            }
            MessageTypes.DATE.ordinal -> {
                val itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.date_message_layout, parent, false)
                DateMessageViewHolder(itemView)
            }
            else -> throw IllegalArgumentException("Illegal type $viewType")
        }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        when (holder) {
            is IncomeMessageViewHolder -> {
                holder.setData(messages[position] as IncomeMessage)
                holder.setOnClickListener ({ v ->
                    listener.addSmileViewClicked(
                        v as ImageView?,
                        holder.getFlexBoxLayout()
                    )}, {v ->
                    listener.messageViewClicked(
                        v as TextView?
                    )}
                )
            }
            is OutcomeMessageViewHolder -> {
                holder.setData(messages[position] as OutcomeMessage)
                holder.setOnClickListener { v ->
                    listener.addSmileViewClicked(
                        v as ImageView?,
                        holder.getFlexBoxLayout()
                    )
                }
            }
            is DateMessageViewHolder -> holder.setData(messages[position] as DateMessage)
        }
    }

    override fun getItemCount() = messages.size

    fun setItems(newMessages: List<Message>) {
        messages = newMessages
        notifyItemInserted(messages.size - 1)
    }
}