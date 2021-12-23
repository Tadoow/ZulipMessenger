package com.example.myapplication.presentation.chat

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.data.dto.event.Event
import com.example.myapplication.data.dto.event.HeartBeatEvent
import com.example.myapplication.data.dto.response.queue.RegisterQueueResponse
import com.example.myapplication.domain.Repository
import com.example.myapplication.domain.entity.channels.Topic
import com.example.myapplication.domain.entity.chat.ChatItem
import com.example.myapplication.domain.entity.chat.Reaction
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ChatViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private var _progressLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val progressLiveData: LiveData<Boolean>
        get() = _progressLiveData

    private var _errorLiveData: MutableLiveData<Throwable> = MutableLiveData()
    val errorLiveData: LiveData<Throwable>
        get() = _errorLiveData

    private var _chatItemsLiveData: MutableLiveData<List<ChatItem>> = MutableLiveData()
    val chatItemsLiveData: LiveData<List<ChatItem>>
        get() = _chatItemsLiveData

    private var _emojiListLiveData: MutableLiveData<List<Reaction>> = MutableLiveData()
    val emojiListLiveData: LiveData<List<Reaction>>
        get() = _emojiListLiveData

    private val _registerQueueLiveData: MutableLiveData<RegisterQueueResponse> = MutableLiveData()
    val registerQueueLiveData: LiveData<RegisterQueueResponse>
        get() = _registerQueueLiveData

    private val _eventsLiveData: MutableLiveData<List<Event>> = MutableLiveData()
    val eventsLiveData: LiveData<List<Event>>
        get() = _eventsLiveData

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    init {
        getEmojiList()
    }

    private fun getEmojiList() {
        repository.getEmojiReactions()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { _emojiListLiveData.value = it },
                onError = { _errorLiveData.value = it }
            )
            .addTo(compositeDisposable)
    }

    fun loadMessagesInTopic(topic: Topic) {
        repository.loadMessagesInTopic(topic)
            .doOnSubscribe { _progressLiveData.postValue(true) }
            .subscribeOn(Schedulers.io())
            .doAfterTerminate { _progressLiveData.postValue(false) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { _chatItemsLiveData.value = it },
                onError = { _errorLiveData.value = it }
            )
            .addTo(compositeDisposable)
    }

    fun sendMessageToTopic(topic: Topic, messageText: String) {
        repository.sendMessageToTopic(topic.streamHostName, topic.topicName, messageText)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { Log.d(TAG, "sendMessageToTopic: ${it.msg}") },
                onError = { _errorLiveData.value = it }
            )
            .addTo(compositeDisposable)
    }

    fun addEmojiReaction(messageId: Int, emoji: Reaction) {
        repository.addEmojiReaction(messageId, emoji.emojiName, emoji.emojiCode)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { Log.d(TAG, "addEmojiReaction: ${it.msg}") },
                onError = { _errorLiveData.value = it }
            )
            .addTo(compositeDisposable)
    }

    fun removeEmojiReaction(messageId: Int, emoji: Reaction) {
        repository.removeEmojiReaction(messageId, emoji.emojiName, emoji.emojiCode)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { Log.d(TAG, "removeEmojiReaction: ${it.msg}") },
                onError = { _errorLiveData.value = it }
            )
            .addTo(compositeDisposable)
    }

    fun registerEventQueue(eventTypes: List<String>, narrow: String) {
        repository.registerEventQueue(eventTypes, eventTypes, narrow)
            .subscribeOn(Schedulers.io())
            .doOnSuccess { Log.d(TAG, "getEventsQueue: $it") }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    _registerQueueLiveData.value = it
                    getEventsQueue(it.id, it.lastId)
                },
                onError = { _errorLiveData.value = it }
            )
            .addTo(compositeDisposable)
    }

    private fun getEventsQueue(queueId: String, lastEventId: Int) {
        Observable.create<List<Event>> { emitter ->
            repository.getEventsFromQueue(queueId, lastEventId, emitter)
                .addTo(compositeDisposable)
        }
            .filter { eventsList -> eventsList.isNotEmpty() }
            .filter { eventsList -> !eventsList.any { it is HeartBeatEvent } }
            .subscribeOn(Schedulers.io())
            .doOnNext { Log.d(TAG, "getEventsQueue: $it") }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = { _eventsLiveData.value = it },
                onError = { _errorLiveData.value = it }
            )
            .addTo(compositeDisposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    companion object {
        const val TAG = "ChatViewModel"
    }

}