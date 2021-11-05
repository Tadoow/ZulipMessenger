package com.example.myapplication.presentation.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.presentation.models.ChannelsItem
import com.example.myapplication.presentation.view.adapters.ChannelsAdapter
import com.example.myapplication.presentation.view.listeners.OnTopicClickListener

class ViewPagerFragment : Fragment(R.layout.fragment_viewpager) {

    private lateinit var channelsItems: List<ChannelsItem>
    private lateinit var channelsAdapter: ChannelsAdapter
    val TAG = "ViewPagerFragment"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate() called with: savedInstanceState = $savedInstanceState")
        channelsItems = requireArguments().getParcelableArrayList("channelsItems")!!
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d(
            TAG,
            "onViewCreated() channelsItems ${channelsItems.size}"
        )

        val channelsRecyclerView = view.findViewById<RecyclerView>(R.id.channels_recycler_view)
        channelsRecyclerView.layoutManager =
            LinearLayoutManager(activity, RecyclerView.VERTICAL, false)

        channelsAdapter = ChannelsAdapter(channelsItems, activity as OnTopicClickListener)
        channelsRecyclerView.adapter = channelsAdapter

        val dividerItemDecoration = DividerItemDecoration(context, RecyclerView.VERTICAL)
        dividerItemDecoration.setDrawable(
            ResourcesCompat.getDrawable(resources, R.drawable.channels_item_divider, null)!!
        )

        channelsRecyclerView.addItemDecoration(dividerItemDecoration)

    }
}