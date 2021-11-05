package com.example.myapplication.presentation.view.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.presentation.models.ChannelTypes
import com.example.myapplication.presentation.models.ChannelsItem
import com.example.myapplication.presentation.models.Stream
import com.example.myapplication.presentation.models.Topic
import com.example.myapplication.presentation.view.listeners.OnTopicClickListener
import com.example.myapplication.presentation.view.viewholders.StreamViewHolder
import com.example.myapplication.presentation.view.viewholders.TopicViewHolder

class ChannelsAdapter(
    private var channelsItemList: List<ChannelsItem>,
    private val topicClickListener: OnTopicClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemViewType(position: Int): Int {
        return channelsItemList[position].getType()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        Log.d("TAG", "onCreateViewHolder() called with: parent = $parent, viewType = $viewType")
        when (viewType) {
            ChannelTypes.STREAM.ordinal -> {
                val itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.stream_item, parent, false)
                return StreamViewHolder(itemView)
            }
            ChannelTypes.TOPIC.ordinal -> {
                val itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.topic_item, parent, false)
                return TopicViewHolder(itemView)
            }
            else -> throw IllegalArgumentException("Such viewType not found: $viewType")
        }
    }

    override fun getItemCount(): Int {
        return channelsItemList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        Log.d("TAG", "onBindViewHolder() called with: holder = $holder, position = $position")
        val item = channelsItemList[position]
        when (holder) {
            is StreamViewHolder -> {
                holder.setData(item)
                holder.itemView.setOnClickListener {
                    toggleStream(holder.adapterPosition)
                    holder.switchStreamState()
                }
            }
            is TopicViewHolder -> {
                holder.setData(item)
                holder.itemView.setOnClickListener {
                    topicClickListener.topicClicked(item)
                }
            }
            else -> throw IllegalArgumentException("Such holder not found: $holder")
        }
    }

    private fun toggleStream(position: Int) {
        val item = channelsItemList[position] as Stream
        val topicsToToggle = arrayListOf<Topic>()
        val currentChannelsItemList = channelsItemList as ArrayList
        for (topic in item.topics) {
            topicsToToggle.add(topic)
        }
        if (item.isExpanded) {
            currentChannelsItemList.removeAll(topicsToToggle)
            channelsItemList = currentChannelsItemList
            notifyItemRangeRemoved(position + 1, topicsToToggle.size)
        } else {
            currentChannelsItemList.addAll(position + 1, topicsToToggle)
            channelsItemList = currentChannelsItemList
            notifyItemRangeInserted(position + 1, topicsToToggle.size)
        }
        item.isExpanded = !item.isExpanded
    }
}