package com.example.myapplication.common

import android.app.Activity
import com.example.myapplication.MessengerApplication
import com.example.myapplication.di.channels.DaggerViewPagerComponent
import com.example.myapplication.di.channels.ViewPagerComponent
import com.example.myapplication.di.chat.ChatComponent
import com.example.myapplication.di.chat.DaggerChatComponent
import com.example.myapplication.di.main.ActivityComponent
import com.example.myapplication.di.main.DaggerActivityComponent
import com.example.myapplication.di.people.DaggerPeopleComponent
import com.example.myapplication.di.people.PeopleComponent
import com.example.myapplication.di.profile.DaggerProfileComponent
import com.example.myapplication.di.profile.ProfileComponent
import com.example.myapplication.presentation.channels.viewpager.ViewPagerFragment
import com.example.myapplication.presentation.chat.ChatFragment
import com.example.myapplication.presentation.people.PeopleFragment
import com.example.myapplication.presentation.profile.ProfileFragment

fun Activity.getActivityComponent(): ActivityComponent =
    DaggerActivityComponent.builder()
        .appComponent(MessengerApplication.getAppComponent(this))
        .build()

fun ViewPagerFragment.getFragmentComponent(): ViewPagerComponent =
    DaggerViewPagerComponent.builder()
        .activityComponent(activity?.getActivityComponent())
        .build()

fun ChatFragment.getFragmentComponent(): ChatComponent =
    DaggerChatComponent.builder()
        .activityComponent(activity?.getActivityComponent())
        .build()

fun PeopleFragment.getFragmentComponent(): PeopleComponent =
    DaggerPeopleComponent.builder()
        .activityComponent(activity?.getActivityComponent())
        .build()

fun ProfileFragment.getFragmentComponent(): ProfileComponent =
    DaggerProfileComponent.builder()
        .activityComponent(activity?.getActivityComponent())
        .build()