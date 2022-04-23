package com.shubham.moviesdb

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MoviesApplication : Application() {
    init {
        instance = this
    }

    companion object {
        private var instance: MoviesApplication? = null
        fun applicationContext(): MoviesApplication {
            return instance as MoviesApplication
        }
    }
}