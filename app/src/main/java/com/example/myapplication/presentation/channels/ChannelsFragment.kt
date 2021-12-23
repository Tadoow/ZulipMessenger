package com.example.myapplication.presentation.channels

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import com.example.myapplication.R
import com.example.myapplication.common.viewBinding
import com.example.myapplication.databinding.FragmentChannelsBinding
import com.example.myapplication.presentation.channels.adapter.ChannelsAdapter
import com.example.myapplication.presentation.channels.viewpager.listener.OnSearchTextListener
import com.google.android.material.tabs.TabLayoutMediator

class ChannelsFragment : Fragment(R.layout.fragment_channels) {

    private val binding by viewBinding(FragmentChannelsBinding::bind)

    private lateinit var channelsAdapter: ChannelsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate() called with: savedInstanceState = $savedInstanceState")
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        channelsAdapter = ChannelsAdapter(this)
        binding.viewPager.adapter = channelsAdapter

        val tabs: List<String> = listOf(SUBSCRIBED, ALL_STREAMS)
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = tabs[position]
        }.attach()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)

        val searchItem: MenuItem = menu.findItem(R.id.search_action)
        val searchView: SearchView = searchItem.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    when (childFragmentManager.fragments.first().isVisible) {
                        true -> (childFragmentManager.fragments.first() as OnSearchTextListener)
                            .search(newText)
                        false -> (childFragmentManager.fragments.last() as OnSearchTextListener)
                            .search(newText)
                    }
                }
                return true
            }
        })
        super.onCreateOptionsMenu(menu, inflater)
    }

    companion object {

        const val TAG = "ChannelsFragment"

        private const val SUBSCRIBED = "Subscribed"
        private const val ALL_STREAMS = "All streams"

        fun newInstance(): Fragment {
            return ChannelsFragment()
        }
    }

}