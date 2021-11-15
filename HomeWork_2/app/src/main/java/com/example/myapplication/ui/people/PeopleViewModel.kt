package com.example.myapplication.ui.people

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.data.repository.Repository
import com.example.myapplication.domain.people.entity.User
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit

internal class PeopleViewModel(private val repository: Repository) : ViewModel() {

    private val _listItemsLiveData: MutableLiveData<List<User>> = MutableLiveData()
    val listItemsLiveData: LiveData<List<User>>
        get() = _listItemsLiveData

    private val _progressLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val progressLiveData: LiveData<Boolean>
        get() = _progressLiveData

    private val _errorLiveData: MutableLiveData<Throwable> = MutableLiveData()
    val errorLiveData: LiveData<Throwable>
        get() = _errorLiveData

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private val searchSubject: PublishSubject<String> = PublishSubject.create()

    init {
        searchProcessing()
    }

    fun searchUser(searchText: String) {
        searchSubject.onNext(searchText)
    }

    private fun searchProcessing() {
        searchSubject
            .distinctUntilChanged()
            .doOnNext { _progressLiveData.postValue(true) }
            .debounce(500, TimeUnit.MILLISECONDS)
            .switchMap { searchText -> repository.searchUsers(searchText).toObservable() }
            .doOnNext { _progressLiveData.postValue(false) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = { _listItemsLiveData.value = it },
                onError = { _errorLiveData.value = it }
            )
            .addTo(compositeDisposable)
    }

    fun loadUsers() {
        repository.loadUsers()
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