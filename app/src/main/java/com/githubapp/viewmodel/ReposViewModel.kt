package com.githubapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.githubapp.model.domain.Repository
import com.githubapp.utils.AppState
import com.githubapp.view.AuthFragment
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers

class ReposViewModel(private val repository: Repository) : ViewModel() {
    companion object {
        private const val CORRUPTED_DATA = "Неполные данные"
    }

    val reposLiveData: MutableLiveData<AppState> = MutableLiveData()
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun getUser() {
        reposLiveData.value = AppState.Loading
        compositeDisposable.add(
            repository.getUser()
                .subscribeOn(Schedulers.io())
                .subscribeBy(
                    onSuccess = {
                        it.toString()
                    },
                    onError = {
                        Error(Throwable(CORRUPTED_DATA))
                    }
                )
        )
    }

    fun getRepos(user : String) {
        reposLiveData.value = AppState.Loading
        compositeDisposable.add(
            repository
                .getRepos(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onSuccess = {
                        reposLiveData.postValue(
                            AppState.Success(it)
                        )
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