package com.githubapp.model.domain

import com.githubapp.model.data.AccessToken
import com.githubapp.model.data.Repo
import com.githubapp.model.data.User
import io.reactivex.rxjava3.core.Single

interface Repository {
    fun getRepos(user : String): Single<List<Repo>>
    fun getUser(): Single<User>
    fun getToken(clientId : String, clientSecret : String, code : String): Single<AccessToken>
}