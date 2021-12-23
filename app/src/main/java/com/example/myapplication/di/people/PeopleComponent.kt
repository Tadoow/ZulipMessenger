package com.example.myapplication.di.people

import com.example.myapplication.di.FragmentScope
import com.example.myapplication.di.main.ActivityComponent
import com.example.myapplication.presentation.people.PeopleViewModel
import dagger.Component

@FragmentScope
@Component(dependencies = [ActivityComponent::class])
interface PeopleComponent {

    fun getPeopleViewModel(): PeopleViewModel
}