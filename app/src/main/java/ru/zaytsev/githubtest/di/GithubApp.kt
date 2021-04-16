package ru.zaytsev.githubtest.di

import android.app.Application
import ru.zaytsev.githubtest.di.component.DaggerMainComponent
import ru.zaytsev.githubtest.di.component.MainComponent
import ru.zaytsev.githubtest.di.module.DataBaseModule

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