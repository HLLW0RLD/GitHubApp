package com.githubapp.model.repository

import com.githubapp.model.RemoteDataSource
import com.githubapp.model.data.AccessToken
import com.githubapp.model.data.Repo
import com.githubapp.model.data.User
import com.githubapp.model.domain.Repository
import io.reactivex.rxjava3.core.Single

class RepositoryImpl(private val remote : RemoteDataSource) : Repository {
    override fun getRepos(user: String): Single<List<Repo>> {
        return remote.getRepos(user)
    }
    override fun getUser(): Single<User> {
        return remote.getUser()
    }

    override fun getToken(clientId : String, clientSecret : String, code : String): Single<AccessToken> {
        return remote.getToken(clientId, clientSecret, code)
    }
}