package com.example.myapplication.ui.channels.viewpager

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.MessengerApplication
import com.example.myapplication.R
import com.example.myapplication.common.viewBinding
import com.example.myapplication.databinding.FragmentViewpagerBinding
import com.example.myapplication.ui.channels.viewpager.adapter.ViewPagerAdapter
import com.example.myapplication.ui.channels.viewpager.listener.OnSearchTextListener
import com.example.myapplication.ui.channels.viewpager.listener.OnTopicClickListener

class ViewPagerFragment : Fragment(R.layout.fragment_viewpager), OnSearchTextListener {

    private val binding by viewBinding(FragmentViewpagerBinding::bind)
    private val fragmentPosition by lazy { requireArguments().getInt("position") }

    private val viewModel: ViewPagerViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return ViewPagerViewModel(fragmentPosition, MessengerApplication.repository) as T
            }
        }
    }

    private lateinit var viewPagerAdapter: ViewPagerAdapter

    private fun observeLiveData() {
        viewModel.progressLiveData.observe(viewLifecycleOwner) { binding.progress.isVisible = it }
        viewModel.listItemsLiveData.observe(viewLifecycleOwner) { viewPagerAdapter.setItems(it) }
        viewModel.errorLiveData.observe(viewLifecycleOwner) {
            Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d(
            TAG,
            "viewmodel $viewModel"
        )
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

    override fun onResume() {
        super.onResume()
        viewModel.searchStream("")
    }

    override fun search(text: String) {
        viewModel.searchStream(text)
    }

    companion object {
        const val TAG = "ViewPagerFragment"
    }
}