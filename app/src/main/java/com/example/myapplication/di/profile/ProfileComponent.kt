package com.example.myapplication.di.profile

import com.example.myapplication.di.FragmentScope
import com.example.myapplication.di.main.ActivityComponent
import com.example.myapplication.presentation.profile.ProfileViewModel
import dagger.Component

@FragmentScope
@Component(dependencies = [ActivityComponent::class])
interface ProfileComponent {

    fun getProfileViewModel(): ProfileViewModel
}