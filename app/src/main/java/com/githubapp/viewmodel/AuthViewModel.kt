package com.githubapp.viewmodel

import android.content.Intent
import android.net.Uri
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.githubapp.model.data.AccessToken
import com.githubapp.model.domain.Repository
import com.githubapp.view.AuthFragment
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers

class AuthViewModel(private val repository: Repository) : ViewModel() {
    companion object {
        private const val CORRUPTED_DATA = "Неполные данные"
    }

    val tokenLiveData: MutableLiveData<AccessToken> = MutableLiveData()
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun getToken(clientId : String, clientSecret : String, code : String) {
        compositeDisposable.add(
            repository
                .getToken(clientId, clientSecret, code)
                .subscribeOn(Schedulers.io())
                .subscribeBy(
                    onSuccess = {
                        tokenLiveData.postValue(it)
                    },
                    onError = {
                        Error(Throwable(CORRUPTED_DATA))
                    }
                )
        )
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}