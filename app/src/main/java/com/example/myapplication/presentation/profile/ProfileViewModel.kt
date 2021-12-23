package com.example.myapplication.presentation.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.domain.Repository
import com.example.myapplication.domain.entity.people.User
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ProfileViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private val _errorLiveData: MutableLiveData<Throwable> = MutableLiveData()
    val errorLiveData: LiveData<Throwable>
        get() = _errorLiveData

    private val _ownUserLiveData: MutableLiveData<User> = MutableLiveData()
    val ownUserLiveData: LiveData<User>
        get() = _ownUserLiveData

    private val _userPresenceLiveData: MutableLiveData<String> = MutableLiveData()
    val userPresenceLiveData: LiveData<String>
        get() = _userPresenceLiveData

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    init {
        _ownUserLiveData.value = repository.getOwnUser()
    }

    fun loadUserPresence() {
        repository.getUserPresence(repository.getOwnUser().userId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { _userPresenceLiveData.value = it },
                onError = { _errorLiveData.value = it }
            )
            .addTo(compositeDisposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

}