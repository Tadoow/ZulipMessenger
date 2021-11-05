package com.example.myapplication.presentation.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.myapplication.R
import com.example.myapplication.presentation.models.ChannelsItem
import com.example.myapplication.presentation.models.User
import com.example.myapplication.presentation.view.fragments.AnotherProfileFragment
import com.example.myapplication.presentation.view.fragments.ChatFragment
import com.example.myapplication.presentation.view.fragments.MainFragment
import com.example.myapplication.presentation.view.listeners.OnTopicClickListener
import com.example.myapplication.presentation.view.listeners.OnUserClickListener

class MainActivity : AppCompatActivity(), OnTopicClickListener, OnUserClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolBar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolBar)

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