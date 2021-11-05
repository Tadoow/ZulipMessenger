package com.example.myapplication.presentation.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.myapplication.R
import com.example.myapplication.presentation.models.ChannelsItem
import com.example.myapplication.presentation.view.adapters.ViewPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class ChannelsFragment : Fragment(R.layout.fragment_channels) {

    private lateinit var viewPagerAdapter: ViewPagerAdapter
    private lateinit var viewPager: ViewPager2
    private lateinit var subscribedStreams: List<ChannelsItem>
    private lateinit var allStreams: List<ChannelsItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate() called with: savedInstanceState = $savedInstanceState")
        setHasOptionsMenu(true)

        subscribedStreams = requireArguments().getParcelableArrayList(SUBSCRIBED)!!
        allStreams = requireArguments().getParcelableArrayList(ALL_STREAMS)!!
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewPager = view.findViewById(R.id.view_pager)

        viewPagerAdapter = ViewPagerAdapter(this, subscribedStreams, allStreams)
        viewPager.adapter = viewPagerAdapter

        val tabs: List<String> = listOf(SUBSCRIBED, ALL_STREAMS)
        val tabLayout = view.findViewById<TabLayout>(R.id.tabLayout)
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = tabs[position]
        }.attach()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)

        val searchItem: MenuItem = menu.findItem(R.id.search_action)
        val searchView: SearchView = searchItem.actionView as SearchView
        super.onCreateOptionsMenu(menu, inflater)
    }

    companion object {

        const val TAG = "ChannelsFragment"

        private const val SUBSCRIBED = "Subscribed"
        private const val ALL_STREAMS = "All streams"

        fun newInstance(
            subscribedStreams: List<ChannelsItem>,
            allStreams: List<ChannelsItem>
        ): Fragment {
            Log.d(
                TAG,
                "newInstance() called with: subscribedStreams = $subscribedStreams, allStreams = $allStreams"
            )
            val fragment = ChannelsFragment()
            fragment.arguments = Bundle().apply {
                putParcelableArrayList(SUBSCRIBED, subscribedStreams as ArrayList)
                putParcelableArrayList(ALL_STREAMS, allStreams as ArrayList)
            }
            return fragment
        }
    }
}