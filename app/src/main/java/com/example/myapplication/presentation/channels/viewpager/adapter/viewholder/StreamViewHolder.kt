package com.example.myapplication.presentation.channels.viewpager.adapter.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.databinding.StreamItemBinding

class StreamViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val binding = StreamItemBinding.bind(itemView)

    fun setStreamName(streamName: String) {
        binding.streamNameTextView.text =
            itemView.resources.getString(R.string.stream_name, streamName)
    }

    fun toggleStreamState() {
        binding.streamExpandImageView.isSelected = !binding.streamExpandImageView.isSelected
    }

    fun setStreamState(expanded: Boolean) {
        binding.streamExpandImageView.isSelected = expanded
    }

}