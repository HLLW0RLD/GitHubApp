package com.githubapp.model

import com.githubapp.model.data.AccessToken
import com.githubapp.model.data.Repo
import com.githubapp.model.data.User
import com.githubapp.model.domain.GitHubApi
import io.reactivex.rxjava3.core.Single

class RemoteDataSource(private val client : GitHubApi) {
    fun getRepos(user : String) : Single<List<Repo>> {
        return client.getRepos(user)
    }
    fun getUser() : Single<User> {
        return client.getUser()
    }
    fun getToken(clientId : String, clientSecret : String, code : String) : Single<AccessToken> {
        return client.getToken(clientId, clientSecret, code)
    }
}