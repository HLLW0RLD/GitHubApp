package com.githubapp.model.domain

import com.githubapp.model.data.AccessToken
import com.githubapp.model.data.Repo
import com.githubapp.model.data.User
import io.reactivex.rxjava3.core.Single
import retrofit2.http.*

interface GitHubApi {

    @Headers("Accept: application/json")
    @POST("login/oauth/access_token")
    @FormUrlEncoded
    fun getToken(
        @Field("client_id") clientId: String,
        @Field("client_secret") clientSecret: String,
        @Field("code") code : String
    ): Single<AccessToken>

    @GET("user")
    fun getUser(): Single<User>

    @GET("users/{user}/repos")
    fun getRepos(@Path("user") user : String): Single<List<Repo>>
}