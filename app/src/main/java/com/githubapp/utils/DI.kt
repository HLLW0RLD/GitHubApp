package com.githubapp.utils

import com.githubapp.model.RemoteDataSource
import com.githubapp.model.domain.GitHubApi
import com.githubapp.model.domain.Repository
import com.githubapp.model.repository.RepositoryImpl
import com.githubapp.viewmodel.AuthViewModel
import com.githubapp.viewmodel.ReposViewModel
import com.google.gson.GsonBuilder
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

private const val mainUrl = "https://api.github.com/"

val appModuleKoin = module {

    viewModel<AuthViewModel> { AuthViewModel(get()) }
    viewModel<ReposViewModel> { ReposViewModel(get()) }
    single<Repository> { RepositoryImpl(RemoteDataSource(get())) }
    single<GitHubApi> { get<Retrofit>().create(GitHubApi::class.java) }
    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl(mainUrl)
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(get())
            .build()
    }
    factory<Converter.Factory> { GsonConverterFactory.create(GsonBuilder().setLenient().create()) }

}