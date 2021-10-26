package com.example.myapplication.view.fragments

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import com.example.myapplication.R

class ChannelsFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_channels, container, false)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.channels_fragment_menu, menu)

        val searchItem: MenuItem = menu.findItem(R.id.search_action)
        val searchView: SearchView = searchItem.actionView as SearchView
        super.onCreateOptionsMenu(menu, inflater)
    }

    companion object {
        fun newInstance(): Fragment {
            return ChannelsFragment()
        }
    }
}