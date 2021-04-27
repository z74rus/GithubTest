package com.example.githubconductor.di

import android.app.Application
import com.example.githubconductor.di.component.DaggerMainComponent
import com.example.githubconductor.di.component.MainComponent
import com.example.githubconductor.di.module.AuthModule
import com.example.githubconductor.di.module.DataBaseModule
import com.example.githubconductor.di.module.SharedPrefModule
import com.example.githubconductor.di.module.TabsIntentModule

class GithubApp: Application() {
    companion object { lateinit var mainComponent: MainComponent }

    override fun onCreate() {
        super.onCreate()
        mainComponent = DaggerMainComponent
            .builder()
            .dataBaseModule(DataBaseModule(this))
            .sharedPrefModule(SharedPrefModule(this))
            .authModule(AuthModule(this))
            .tabsIntentModule(TabsIntentModule(this))
            .build()
    }
}