package com.example.githubconductor.di

import android.app.Application
import com.example.githubconductor.di.component.DaggerMainComponent
import com.example.githubconductor.di.component.MainComponent
import com.example.githubconductor.di.module.*

class GithubApp: Application() {
    companion object { lateinit var mainComponent: MainComponent }

    override fun onCreate() {
        super.onCreate()
        mainComponent = DaggerMainComponent
            .builder()
            .appModule(AppModule(this))
            .build()
    }
}