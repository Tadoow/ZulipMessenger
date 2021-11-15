package com.example.myapplication.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.domain.channels.entity.ChannelsItem
import com.example.myapplication.domain.people.entity.User
import com.example.myapplication.ui.another_profile.AnotherProfileFragment
import com.example.myapplication.ui.channels.viewpager.listener.OnTopicClickListener
import com.example.myapplication.ui.chat.ChatFragment
import com.example.myapplication.ui.people.listener.OnUserClickListener

class MainActivity : AppCompatActivity(), OnTopicClickListener, OnUserClickListener {

    private val binding: ActivityMainBinding by lazy(LazyThreadSafetyMode.NONE) {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar.toolbar)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.main_fragment_container, MainFragment.newInstance())
                .commit()
        }
    }

    override fun topicClicked(channelsItem: ChannelsItem) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_fragment_container, ChatFragment.newInstance(channelsItem))
            .addToBackStack(null)
            .commit()
    }

    override fun userClicked(user: User) {
        supportFragmentManager.beginTransaction()
            .add(R.id.main_fragment_container, AnotherProfileFragment.newInstance(user))
            .addToBackStack(null)
            .commit()
    }
}