package com.example.myapplication.di.main

import com.example.myapplication.data.repository.RepositoryImpl
import com.example.myapplication.di.ActivityScope
import com.example.myapplication.domain.Repository
import dagger.Binds
import dagger.Module

@Module
interface BindsModule {

    @ActivityScope
    @Binds
    fun bindRepository(repositoryImpl: RepositoryImpl): Repository
}