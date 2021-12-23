package com.example.myapplication.presentation.channels.viewpager.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.domain.entity.channels.ChannelTypes
import com.example.myapplication.domain.entity.channels.ChannelsItem
import com.example.myapplication.domain.entity.channels.Stream
import com.example.myapplication.domain.entity.channels.Topic
import com.example.myapplication.presentation.channels.viewpager.adapter.viewholder.StreamViewHolder
import com.example.myapplication.presentation.channels.viewpager.adapter.viewholder.TopicViewHolder
import com.example.myapplication.presentation.channels.viewpager.listener.OnTopicClickListener

class ViewPagerAdapter(
    private var channelsItems: List<ChannelsItem> = emptyList(),
    private val topicClickListener: OnTopicClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemViewType(position: Int): Int {
        return channelsItems[position].getType()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        Log.d("TAG", "onCreateViewHolder() called with: parent = $parent, viewType = $viewType")
        return when (viewType) {
            ChannelTypes.STREAM.ordinal -> {
                val itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.stream_item, parent, false)
                StreamViewHolder(itemView)
            }
            ChannelTypes.TOPIC.ordinal -> {
                val itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.topic_item, parent, false)
                TopicViewHolder(itemView)
            }
            else -> throw IllegalArgumentException("Such viewType not found: $viewType")
        }
    }

    override fun getItemCount(): Int {
        return channelsItems.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        Log.d("TAG", "onBindViewHolder() called with: holder = $holder, position = $position")
        val item = channelsItems[position]
        when (holder) {
            is StreamViewHolder -> StreamItemBinder.bind(holder, item as Stream, this)
            is TopicViewHolder -> TopicItemBinder.bind(holder, item as Topic, topicClickListener)
            else -> throw IllegalArgumentException("Such holder not found: $holder")
        }
    }

    fun toggleStream(position: Int) {
        val topicsToToggle = arrayListOf<Topic>()
        val item = channelsItems[position] as Stream
        for (topic in item.topics) {
            topicsToToggle.add(topic)
        }
        if (item.isExpanded) {
            (channelsItems as ArrayList).removeAll(topicsToToggle)
            notifyItemRangeRemoved(position + 1, topicsToToggle.size)
        } else {
            (channelsItems as ArrayList).addAll(position + 1, topicsToToggle)
            notifyItemRangeInserted(position + 1, topicsToToggle.size)
        }
        item.isExpanded = !item.isExpanded
    }

    fun setItems(channelsItemList: List<ChannelsItem>) {
        this.channelsItems = channelsItemList
        notifyDataSetChanged()
    }

}