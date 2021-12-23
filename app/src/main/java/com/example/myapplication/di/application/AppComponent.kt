package com.example.myapplication.di.application

import com.example.myapplication.data.api.MessengerApi
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun getMessengerApi(): MessengerApi
}