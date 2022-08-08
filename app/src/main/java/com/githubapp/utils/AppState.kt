package com.githubapp.utils

import com.githubapp.model.data.Repo

sealed class AppState{
    object Loading : AppState()
    object Error : AppState()
    class Success(repos : List<Repo>): AppState()
}
