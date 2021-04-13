package ru.zaytsev.githubtest.utils

import android.app.Application
import ru.zaytsev.githubtest.data.MainRepository

class UserDetailIntent: Application() {
    override fun onCreate() {
        super.onCreate()
        MainRepository.initialize(context = this)
    }
}