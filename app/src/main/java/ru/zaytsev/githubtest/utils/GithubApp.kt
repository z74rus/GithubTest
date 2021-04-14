package ru.zaytsev.githubtest.utils

import android.app.Application
import ru.zaytsev.githubtest.data.MainRepository
import ru.zaytsev.githubtest.database.DataBase

class GithubApp: Application() {
    override fun onCreate() {
        super.onCreate()
        DataBase.init(this)
    }
}