package com.example.myapplication.di.main

import com.example.myapplication.di.ActivityScope
import com.example.myapplication.di.application.AppComponent
import com.example.myapplication.domain.Repository
import com.example.myapplication.presentation.main.MainActivityViewModel
import dagger.Component

@ActivityScope
@Component(modules = [BindsModule::class], dependencies = [AppComponent::class])
interface ActivityComponent {

    fun getMainActivityViewModel(): MainActivityViewModel

    fun getRepository(): Repository
}