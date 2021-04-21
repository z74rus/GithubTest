package com.example.githubmvp.di

import android.app.Application
import com.example.githubmvp.di.component.DaggerMainComponent
import com.example.githubmvp.di.component.MainComponent
import com.example.githubmvp.di.module.DataBaseModule

class GithubApp: Application() {
    companion object { lateinit var mainComponent: MainComponent }

    override fun onCreate() {
        super.onCreate()
        mainComponent = DaggerMainComponent
            .builder()
            .dataBaseModule(DataBaseModule(this))
            .build()
    }
}