package com.example.myapplication.data.repository

import com.example.myapplication.data.provider.DataProvider
import com.example.myapplication.domain.channels.entity.ChannelsItem
import com.example.myapplication.domain.channels.entity.Stream
import com.example.myapplication.domain.people.entity.User
import io.reactivex.Single
import java.util.concurrent.TimeUnit

class RepositoryImpl(private val provider: DataProvider) : Repository {

    override fun loadAllStreams(): Single<List<ChannelsItem>> {
        return Single.fromCallable { provider.getAllStreams() }
            .delay(1000L, TimeUnit.MILLISECONDS)
    }

    override fun searchInAllStreams(search: String): Single<List<ChannelsItem>> {
        return loadAllStreams()
            .map { streams ->
                streamFilter(streams, search)
            }
            .delay(250L, TimeUnit.MILLISECONDS)
    }

    override fun loadSubscribedStreams(): Single<List<ChannelsItem>> {
        return Single.fromCallable { provider.getSubscribedStreams() }
            .delay(500L, TimeUnit.MILLISECONDS)
    }

    override fun searchInSubscribedStreams(search: String): Single<List<ChannelsItem>> {
        return loadSubscribedStreams()
            .map { streams ->
                streamFilter(streams, search)
            }
    }

    override fun loadUsers(): Single<List<User>> {
        return Single.fromCallable { provider.getUsers() }
            .delay(400L, TimeUnit.MILLISECONDS)
    }

    override fun searchUsers(search: String): Single<List<User>> {
        return loadUsers()
            .map { users ->
                userFilter(users, search)
            }
    }

    private fun streamFilter(streams: List<ChannelsItem>, search: String) = streams.filter {
        it is Stream && it.streamName.contains(search, ignoreCase = true)
    }

    private fun userFilter(users: List<User>, search: String) = users.filter {
        it.userName.contains(search, ignoreCase = true)
    }
}