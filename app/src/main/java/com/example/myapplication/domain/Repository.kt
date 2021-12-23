package com.example.myapplication.domain

import com.example.myapplication.data.dto.event.Event
import com.example.myapplication.data.dto.response.Response
import com.example.myapplication.data.dto.response.queue.RegisterQueueResponse
import com.example.myapplication.domain.entity.channels.ChannelsItem
import com.example.myapplication.domain.entity.channels.Topic
import com.example.myapplication.domain.entity.chat.ChatItem
import com.example.myapplication.domain.entity.chat.Reaction
import com.example.myapplication.domain.entity.people.User
import io.reactivex.ObservableEmitter
import io.reactivex.Single
import io.reactivex.disposables.Disposable

interface Repository {

    fun getOwnUser(): User

    fun loadUsers(): Single<List<User>>

    fun loadAllStreams(): Single<List<ChannelsItem>>

    fun loadSubscribedStreams(): Single<List<ChannelsItem>>

    fun loadMessagesInTopic(topic: Topic): Single<List<ChatItem>>

    fun sendMessageToTopic(streamName: String, topicName: String, text: String): Single<Response>

    fun addEmojiReaction(messageId: Int, emojiName: String, emojiCode: String): Single<Response>

    fun removeEmojiReaction(messageId: Int, emojiName: String, emojiCode: String): Single<Response>

    fun getEmojiReactions(): Single<List<Reaction>>

    fun loadOwnUser(): Disposable

    fun getUserPresence(userId: Int): Single<String>

    fun registerEventQueue(
        eventTypes: List<String>,
        fetchEventTypes: List<String>,
        narrow: String? = null
    ): Single<RegisterQueueResponse>

    fun getEventsFromQueue(
        queueId: String,
        lastEventId: Int,
        emitter: ObservableEmitter<List<Event>>
    ): Disposable

    fun deleteEventQueue(queueId: String): Single<Response>

}