package com.example.githubconductor.di.module

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class SharedPrefModule {

    @Singleton
    @Provides
    fun provideSharedPref(context: Context): SharedPreferences {
        return context.getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)
    }
}