package com.example.myapplication

import android.app.Application
import android.content.Context
import com.example.myapplication.di.application.AppComponent
import com.example.myapplication.di.application.DaggerAppComponent

class MessengerApplication : Application() {

    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.create()
    }

    companion object {

        fun getAppComponent(context: Context): AppComponent {
            return (context.applicationContext as MessengerApplication).appComponent
        }
    }

}