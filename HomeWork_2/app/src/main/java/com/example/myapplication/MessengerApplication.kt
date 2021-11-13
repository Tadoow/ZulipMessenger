package com.example.myapplication

import android.app.Application
import com.example.myapplication.data.provider.DataProvider
import com.example.myapplication.data.repository.Repository
import com.example.myapplication.data.repository.RepositoryImpl

class MessengerApplication : Application() {

    companion object {
        private val dataProvider: DataProvider = DataProvider()
        val repository: Repository = RepositoryImpl(dataProvider)
    }
}