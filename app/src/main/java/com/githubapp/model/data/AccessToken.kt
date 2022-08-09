package com.githubapp.model.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class AccessToken(@SerializedName("access_token") private val accessToken: String,
                       @SerializedName("token_type") private val tokenType: String): Parcelable
