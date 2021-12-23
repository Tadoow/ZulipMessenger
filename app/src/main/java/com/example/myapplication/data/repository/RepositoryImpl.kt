package com.example.myapplication.data.repository

import android.util.Log
import com.example.myapplication.data.api.MessengerApi
import com.example.myapplication.data.dto.Narrow
import com.example.myapplication.data.dto.StreamDto
import com.example.myapplication.data.dto.event.Event
import com.example.myapplication.data.dto.response.Response
import com.example.myapplication.data.dto.response.queue.RegisterQueueResponse
import com.example.myapplication.data.mapper.*
import com.example.myapplication.domain.Repository
import com.example.myapplication.domain.entity.channels.ChannelsItem
import com.example.myapplication.domain.entity.channels.Topic
import com.example.myapplication.domain.entity.chat.ChatItem
import com.example.myapplication.domain.entity.chat.Date
import com.example.myapplication.domain.entity.chat.Reaction
import com.example.myapplication.domain.entity.people.User
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

class RepositoryImpl @Inject constructor(private val messengerApi: MessengerApi) : Repository {

    private val streamDtoToEntityMapper = StreamDtoToEntityMapper()
    private val topicDtoToEntityMapper = TopicDtoToEntityMapper()
    private val userDtoToEntityMapper = UserDtoToEntityMapper()
    private val messageDtoToEntityMapper = MessageDtoToEntityMapper()
    private val reactionsResponseToEntityMapper = ReactionsResponseToEntityMapper()

    init {
        loadOwnUser()
    }

    override fun getOwnUser(): User {
        return owner
    }

    override fun loadUsers(): Single<List<User>> {
        return messengerApi.getUsers()
            .map { usersList -> usersList.members.filter { it.activation && !it.bot } }
            .map { it.map(userDtoToEntityMapper) }
    }

    override fun loadAllStreams(): Single<List<ChannelsItem>> {
        return messengerApi.getAllStreams()
            .flatMap { streamList ->
                Observable.fromIterable(streamList.streams)
                    .concatMapSingle { stream ->
                        loadTopicsInStream(stream)
                            .map { streamDtoToEntityMapper(stream, it) }
                    }
                    .toList()
            }
    }

    override fun loadSubscribedStreams(): Single<List<ChannelsItem>> {
        return messengerApi.getSubscriptions()
            .flatMap { subscriptionsList ->
                Observable.fromIterable(subscriptionsList.subscriptions)
                    .concatMapSingle { stream ->
                        loadTopicsInStream(stream)
                            .map { streamDtoToEntityMapper(stream, it) }
                    }
                    .toList()
            }
    }

    private fun loadTopicsInStream(stream: StreamDto): Single<List<Topic>> {
        return messengerApi.getTopicsInStream(stream.id)
            .map { topicDtoToEntityMapper(it.topics, stream) }
    }

    override fun loadMessagesInTopic(topic: Topic): Single<List<ChatItem>> {
        val narrowList = listOf(
            Json.encodeToString(Narrow(topic.streamHostName, "stream")),
            Json.encodeToString(Narrow(topic.topicName, "topic"))
        ).toString()

        return messengerApi.getMessagesFromTopic(narrow = narrowList)
            .map { it.messages.map(messageDtoToEntityMapper).flatten() }
            .map { chatItems -> dateFilter(chatItems) }
    }

    override fun sendMessageToTopic(
        streamName: String,
        topicName: String,
        text: String
    ): Single<Response> {
        return messengerApi.sendMessageToTopic(
            streamName = streamName,
            topicName = topicName,
            text = text
        )
    }

    override fun addEmojiReaction(
        messageId: Int,
        emojiName: String,
        emojiCode: String
    ): Single<Response> {
        return messengerApi.addEmojiReaction(
            messageId = messageId,
            emojiName = emojiName,
            emojiCode = emojiCode
        )
    }

    override fun removeEmojiReaction(
        messageId: Int,
        emojiName: String,
        emojiCode: String
    ): Single<Response> {
        return messengerApi.removeEmojiReaction(
            messageId = messageId,
            emojiName = emojiName,
            emojiCode = emojiCode
        )
    }

    override fun getEmojiReactions(): Single<List<Reaction>> {
        return messengerApi.getEmojiReactions()
            .map { it.nameToCodePoint }
            .map { reactionsResponseToEntityMapper(it, owner) }
    }

    override fun loadOwnUser(): Disposable {
        return messengerApi.getOwnUser()
            .map(userDtoToEntityMapper)
            .subscribeOn(Schedulers.io())
            .subscribeBy(
                onSuccess = { owner = it },
                onError = { Log.d(TAG, "loadOwnUser: ${it.message}") }
            )
    }

    override fun getUserPresence(userId: Int): Single<String> {
        return messengerApi.getUserPresence(userId)
            .map { it.presence.aggregated?.status }
    }

    override fun registerEventQueue(
        eventTypes: List<String>, fetchEventTypes: List<String>, narrow: String?
    ): Single<RegisterQueueResponse> {
        return messengerApi.registerEventQueue(
            eventTypes = eventTypes.toString(),
            fetchEventTypes = fetchEventTypes.toString(),
            narrow = narrow
        )
    }

    override fun getEventsFromQueue(
        queueId: String, lastEventId: Int, emitter: ObservableEmitter<List<Event>>
    ): Disposable {
        return messengerApi.getEventsFromQueue(queueId, lastEventId)
            .filter { it.events.isNotEmpty() }
            .subscribeBy(
                onNext = {
                    emitter.onNext(it.events)
                    getEventsFromQueue(it.id, it.events.first().id, emitter)
                },
                onError = { emitter.tryOnError(it) }
            )
    }

    override fun deleteEventQueue(queueId: String): Single<Response> {
        return messengerApi.deleteEventQueue(queueId)
    }

    private fun dateFilter(chatItems: List<ChatItem>): List<ChatItem> {
        var prevDateIndex: Int = (chatItems as ArrayList).lastIndex
        for (i in chatItems.indices.reversed()) {
            if (chatItems[i] is Date) {
                if (chatItems[i] == chatItems[prevDateIndex]) {
                    chatItems.removeAt(prevDateIndex)
                }
                prevDateIndex = i
            }
        }
        return chatItems
    }

    companion object {

        const val TAG = "RepositoryImpl"
        lateinit var owner: User
    }

}