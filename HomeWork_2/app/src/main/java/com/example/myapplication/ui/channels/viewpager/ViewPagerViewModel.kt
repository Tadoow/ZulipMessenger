package com.example.myapplication.ui.channels.viewpager

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.data.repository.Repository
import com.example.myapplication.domain.channels.entity.ChannelsItem
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit

internal class ViewPagerViewModel(
    private val fragmentPosition: Int,
    private val repository: Repository
) : ViewModel() {

    private var _progressLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val progressLiveData: LiveData<Boolean>
        get() = _progressLiveData

    private var _listItemsLiveData: MutableLiveData<List<ChannelsItem>> = MutableLiveData()
    val listItemsLiveData: LiveData<List<ChannelsItem>>
        get() = _listItemsLiveData

    private var _errorLiveData: MutableLiveData<Throwable> = MutableLiveData()
    val errorLiveData: LiveData<Throwable>
        get() = _errorLiveData

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private val searchSubject: PublishSubject<String> = PublishSubject.create()

    init {
        searchProcessing()
    }

    fun searchStream(searchText: String) {
        searchSubject.onNext(searchText)
    }

    private fun searchProcessing() {
        searchSubject
            .distinctUntilChanged()
            .debounce(500, TimeUnit.MILLISECONDS)
            .subscribeBy(
                onNext = {
                    if (fragmentPosition == 0) {
                        loadProcessing(repository.searchInSubscribedStreams(it))
                    } else {
                        loadProcessing(repository.searchInAllStreams(it))
                    }
                }
            )
            .addTo(compositeDisposable)
    }

    private fun loadProcessing(observable: Single<List<ChannelsItem>>) {
        observable
            .doOnSubscribe { _progressLiveData.postValue(true) }
            .subscribeOn(Schedulers.io())
            .doAfterTerminate { _progressLiveData.postValue(false) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { _listItemsLiveData.value = it },
                onError = { _errorLiveData.value = it }
            )
            .addTo(compositeDisposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}