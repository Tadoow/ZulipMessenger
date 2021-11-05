package com.example.myapplication.presentation.view.viewholders

import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.presentation.models.ChannelsItem
import com.example.myapplication.presentation.models.Stream

class StreamViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val textView: TextView
    private val expandButton: ImageButton

    init {
        textView = itemView.findViewById(R.id.stream_name_text_view)
        expandButton = itemView.findViewById(R.id.stream_expand_image_view)
    }

    fun switchStreamState() {
        expandButton.isSelected = !expandButton.isSelected
    }

    fun setData(data: ChannelsItem) {
        textView.text = (data as Stream).streamName
    }

}