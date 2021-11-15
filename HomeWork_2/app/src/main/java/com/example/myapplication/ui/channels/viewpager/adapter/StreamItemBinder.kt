package com.example.myapplication.ui.channels.viewpager.adapter

import com.example.myapplication.domain.channels.entity.Stream
import com.example.myapplication.ui.channels.viewpager.adapter.viewholder.StreamViewHolder

object StreamItemBinder {

    fun bind(holder: StreamViewHolder, stream: Stream, adapter: ViewPagerAdapter) {
        holder.setStreamState(stream.isExpanded)
        holder.setStreamName(stream.streamName)

        holder.itemView.setOnClickListener {
            adapter.toggleStream(holder.adapterPosition)
            holder.toggleStreamState()
        }
    }
}