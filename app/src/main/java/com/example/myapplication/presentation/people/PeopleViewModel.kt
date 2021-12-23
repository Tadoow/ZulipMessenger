package com.example.myapplication.presentation.people

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.data.dto.event.Event
import com.example.myapplication.data.dto.event.HeartBeatEvent
import com.example.myapplication.data.dto.response.queue.RegisterQueueResponse
import com.example.myapplication.domain.Repository
import com.example.myapplication.domain.entity.people.User
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class PeopleViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private val _usersLiveData: MutableLiveData<List<User>> = MutableLiveData()
    val usersLiveData: LiveData<List<User>>
        get() = _usersLiveData

    private val _registerQueueLiveData: MutableLiveData<RegisterQueueResponse> = MutableLiveData()
    val registerQueueLiveData: LiveData<RegisterQueueResponse>
        get() = _registerQueueLiveData

    private val _eventsLiveData: MutableLiveData<List<Event>> = MutableLiveData()
    val eventsLiveData: LiveData<List<Event>>
        get() = _eventsLiveData

    private val _progressLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val progressLiveData: LiveData<Boolean>
        get() = _progressLiveData

    private val _errorLiveData: MutableLiveData<Throwable> = MutableLiveData()
    val errorLiveData: LiveData<Throwable>
        get() = _errorLiveData

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun loadUsers() {
        repository.loadUsers()
            .doOnSubscribe { _progressLiveData.postValue(true) }
            .subscribeOn(Schedulers.io())
            .doAfterTerminate { _progressLiveData.postValue(false) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { _usersLiveData.value = it },
                onError = { _errorLiveData.value = it }
            )
            .addTo(compositeDisposable)
    }

    fun registerEventQueue(eventTypes: List<String>) {
        repository.registerEventQueue(eventTypes, eventTypes)
            .subscribeOn(Schedulers.io())
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
            .filter { eventsList -> !eventsList.any { it is HeartBeatEvent } }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = { _eventsLiveData.value = it },
                onError = { _errorLiveData.value = it }
            )
            .addTo(compositeDisposable)
    }

    fun deleteEventQueue(queueId: String) {
        repository.deleteEventQueue(queueId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { Log.d(TAG, "deleteEventQueue: ${it.msg}") },
                onError = { _errorLiveData.value = it }
            )
            .addTo(compositeDisposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    companion object {
        const val TAG = "PeopleViewModel"
    }

}