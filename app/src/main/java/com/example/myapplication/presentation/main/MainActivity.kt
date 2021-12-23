package com.example.myapplication.presentation.main

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import com.example.myapplication.common.getActivityComponent
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.domain.entity.channels.Topic
import com.example.myapplication.presentation.Factory
import com.example.myapplication.presentation.channels.viewpager.listener.OnTopicClickListener
import com.example.myapplication.presentation.chat.ChatFragment
import com.example.myapplication.presentation.main.listener.OnBackPressedListener
import com.example.myapplication.presentation.main.listener.OnBottomNavigationViewListener

class MainActivity : AppCompatActivity(), OnTopicClickListener, OnBottomNavigationViewListener,
    OnBackPressedListener {

    private val binding: ActivityMainBinding by lazy(LazyThreadSafetyMode.NONE) {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val viewModel: MainActivityViewModel by viewModels {
        Factory {
            getActivityComponent().getMainActivityViewModel()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar.toolbar)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.main_fragment_container, MainFragment.newInstance(), MAIN_FRAGMENT_TAG)
                .commit()
        }

        observeLiveData()
    }

    private fun observeLiveData() {
        viewModel.errorLiveData.observe(this) {
            Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
        }
    }

    override fun topicClicked(topic: Topic) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_fragment_container, ChatFragment.newInstance(topic))
            .addToBackStack(null)
            .commit()
    }

    override fun visibilityChanged() {
        val mainFragment =
            supportFragmentManager.findFragmentByTag(MAIN_FRAGMENT_TAG) as MainFragment
        mainFragment.toggleBottomNavigationViewVisibility()
    }

    companion object {
        const val MAIN_FRAGMENT_TAG = "MainFragment"
    }

    override fun backPressedInChat(queueId: String) {
        viewModel.deleteEventQueue(queueId)
    }

}