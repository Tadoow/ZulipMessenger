package com.example.myapplication.data.repository

import com.example.myapplication.domain.channels.entity.ChannelsItem
import com.example.myapplication.domain.people.entity.User
import io.reactivex.Single

interface Repository {

    fun loadAllStreams(): Single<List<ChannelsItem>>

    fun searchInAllStreams(search: String): Single<List<ChannelsItem>>

    fun loadSubscribedStreams(): Single<List<ChannelsItem>>

    fun searchInSubscribedStreams(search: String): Single<List<ChannelsItem>>

    fun loadUsers(): Single<List<User>>

    fun searchUsers(search: String): Single<List<User>>
}