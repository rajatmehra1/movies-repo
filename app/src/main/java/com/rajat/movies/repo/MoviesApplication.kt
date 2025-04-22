package com.rajat.movies.repo

import android.app.Application

class MoviesApplication : Application(){

    lateinit var appContainer: AppContainer
        private set

    override fun onCreate() {
        super.onCreate()
        appContainer = AppContainer(applicationContext)
    }
}