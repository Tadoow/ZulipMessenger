package com.example.myapplication.presentation.view.adapters

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.myapplication.presentation.models.ChannelsItem
import com.example.myapplication.presentation.view.fragments.ViewPagerFragment

class ViewPagerAdapter(
    fragment: Fragment,
    private val subscribedStreams: List<ChannelsItem>,
    private val allStreams: List<ChannelsItem>
) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        val fragment = ViewPagerFragment()
        val streams = if (position == 0) subscribedStreams else allStreams
        fragment.arguments = Bundle().apply {
            putParcelableArrayList("channelsItems", streams as ArrayList)
        }
        return fragment
    }
}