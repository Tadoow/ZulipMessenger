package com.example.myapplication.presentation.channels.viewpager

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.domain.Repository
import com.example.myapplication.domain.entity.channels.ChannelsItem
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ViewPagerViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private var _progressLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val progressLiveData: LiveData<Boolean>
        get() = _progressLiveData

    private var _channelsItemsLiveData: MutableLiveData<List<ChannelsItem>> = MutableLiveData()
    val channelsItemsLiveData: LiveData<List<ChannelsItem>>
        get() = _channelsItemsLiveData

    private var _errorLiveData: MutableLiveData<Throwable> = MutableLiveData()
    val errorLiveData: LiveData<Throwable>
        get() = _errorLiveData

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    private fun loadProcessing(observable: Single<List<ChannelsItem>>) {
        observable
            .doOnSubscribe { _progressLiveData.postValue(true) }
            .subscribeOn(Schedulers.io())
            .doAfterTerminate { _progressLiveData.postValue(false) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { _channelsItemsLiveData.value = it },
                onError = { _errorLiveData.value = it }
            )
            .addTo(compositeDisposable)
    }

    fun loadAllStreams() {
        loadProcessing(repository.loadAllStreams())
    }

    fun loadSubscribedStreams() {
        loadProcessing(repository.loadSubscribedStreams())
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

}