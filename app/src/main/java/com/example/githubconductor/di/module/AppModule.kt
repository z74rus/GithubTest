package com.example.githubconductor.di.module

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.Reusable
@Module
class AppModule(private val application: Application) {
    @Reusable
    @Provides
    fun provideContext(): Context = application
}