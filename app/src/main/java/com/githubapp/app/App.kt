package com.githubapp.app

import android.app.Application
import com.githubapp.utils.appModuleKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application(){
    companion object{
        private var appInstance: App? = null
    }
    override fun onCreate() {
        super.onCreate()
        appInstance = this
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(appModuleKoin)
        }
    }
}