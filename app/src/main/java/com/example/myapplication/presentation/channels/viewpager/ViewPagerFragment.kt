package com.example.myapplication.presentation.channels.viewpager

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.common.getFragmentComponent
import com.example.myapplication.common.viewBinding
import com.example.myapplication.databinding.FragmentViewpagerBinding
import com.example.myapplication.domain.entity.channels.ChannelsItem
import com.example.myapplication.domain.entity.channels.Stream
import com.example.myapplication.domain.entity.channels.Topic
import com.example.myapplication.presentation.Factory
import com.example.myapplication.presentation.channels.viewpager.adapter.ViewPagerAdapter
import com.example.myapplication.presentation.channels.viewpager.listener.OnSearchTextListener
import com.example.myapplication.presentation.channels.viewpager.listener.OnTopicClickListener

class ViewPagerFragment : Fragment(R.layout.fragment_viewpager), OnSearchTextListener {

    private val binding by viewBinding(FragmentViewpagerBinding::bind)
    private val fragmentPosition by lazy { requireArguments().getInt("position") }

    private val viewModel: ViewPagerViewModel by viewModels {
        Factory {
            getFragmentComponent().getViewPagerViewModel()
        }
    }

    private lateinit var viewPagerAdapter: ViewPagerAdapter
    private lateinit var streams: List<ChannelsItem>

    private fun observeLiveData() {
        viewModel.progressLiveData.observe(viewLifecycleOwner) { binding.progress.isVisible = it }
        viewModel.channelsItemsLiveData.observe(viewLifecycleOwner) {
            streams = it
            viewPagerAdapter.setItems(streams)
        }
        viewModel.errorLiveData.observe(viewLifecycleOwner) {
            Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            if (fragmentPosition == 0) {
                viewModel.loadSubscribedStreams()
            } else {
                viewModel.loadAllStreams()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        observeLiveData()

        binding.channelsRecyclerView.layoutManager =
            LinearLayoutManager(activity, RecyclerView.VERTICAL, false)

        viewPagerAdapter = ViewPagerAdapter(topicClickListener = activity as OnTopicClickListener)
        binding.channelsRecyclerView.adapter = viewPagerAdapter

        val dividerItemDecoration = DividerItemDecoration(context, RecyclerView.VERTICAL)
        dividerItemDecoration.setDrawable(
            ResourcesCompat.getDrawable(resources, R.drawable.channels_item_divider, null)!!
        )

        binding.channelsRecyclerView.addItemDecoration(dividerItemDecoration)
    }

    override fun search(text: String) {
        val filteredStreamsList = filter(streams, text)
        viewPagerAdapter.setItems(filteredStreamsList)
        binding.channelsRecyclerView.smoothScrollToPosition(0)
    }

    private fun filter(channels: List<ChannelsItem>, newText: String?): List<ChannelsItem> {
        if (newText != null) {
            val text = newText.lowercase()
            return channels.filter { innerFilter(it, text, channels) }
        }
        return streams
    }

    private fun innerFilter(
        channelsItem: ChannelsItem,
        text: String,
        list: List<ChannelsItem>
    ) = (channelsItem is Stream && channelsItem.streamName.lowercase().contains(text)) ||
            (channelsItem is Topic && channelsItem.streamHostName.lowercase().contains(text) &&
                    (list.find {
                        it is Stream && it.streamName.contains(channelsItem.streamHostName)
                    } as Stream).isExpanded)

    companion object {
        const val TAG = "ViewPagerFragment"
    }

}