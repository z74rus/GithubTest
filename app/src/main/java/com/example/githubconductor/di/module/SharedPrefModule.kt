package com.example.githubconductor.di.module

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class SharedPrefModule(private val application: Application) {

    @Singleton
    @Provides
    fun provideSharedPref(): SharedPreferences {
        return application.getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)
    }
}