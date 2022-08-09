package com.githubapp.model.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Repo(
    val name: String,
    val owner: User,
    val image: User,
    val description: String,
    val forks_count: Int,
    val watchers_count: Int,
    val created_at: String
) : Parcelable

