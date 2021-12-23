package com.example.myapplication.di.channels

import com.example.myapplication.di.FragmentScope
import com.example.myapplication.di.main.ActivityComponent
import com.example.myapplication.presentation.channels.viewpager.ViewPagerViewModel
import dagger.Component

@FragmentScope
@Component(dependencies = [ActivityComponent::class])
interface ViewPagerComponent {

    fun getViewPagerViewModel(): ViewPagerViewModel
}