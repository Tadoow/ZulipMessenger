package com.example.myapplication.ui.channels.viewpager.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.domain.channels.entity.ChannelTypes
import com.example.myapplication.domain.channels.entity.ChannelsItem
import com.example.myapplication.domain.channels.entity.Stream
import com.example.myapplication.domain.channels.entity.Topic
import com.example.myapplication.ui.channels.viewpager.adapter.viewholder.StreamViewHolder
import com.example.myapplication.ui.channels.viewpager.adapter.viewholder.TopicViewHolder
import com.example.myapplication.ui.channels.viewpager.listener.OnTopicClickListener

class ViewPagerAdapter(
    private var channelsItemList: List<ChannelsItem> = emptyList(),
    private val topicClickListener: OnTopicClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemViewType(position: Int): Int {
        return channelsItemList[position].getType()
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
        return channelsItemList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        Log.d("TAG", "onBindViewHolder() called with: holder = $holder, position = $position")
        val item = channelsItemList[position]
        when (holder) {
            is StreamViewHolder -> StreamItemBinder.bind(holder, item as Stream, this)
            is TopicViewHolder -> TopicItemBinder.bind(holder, item as Topic, topicClickListener)
            else -> throw IllegalArgumentException("Such holder not found: $holder")
        }
    }

    fun toggleStream(position: Int) {
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

    fun setItems(channelsItemList: List<ChannelsItem>) {
        this.channelsItemList = channelsItemList
        notifyDataSetChanged()
    }
}