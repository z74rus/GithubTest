package ru.zaytsev.githubtest.di

import ru.zaytsev.githubtest.data.MainRepository
import ru.zaytsev.githubtest.database.DataBase
import ru.zaytsev.githubtest.ui.main.MainViewModel

object DiContainer {

    private fun getMainRepository(): MainRepository {
        return MainRepository(DataBase.instance.userDao())
    }

    fun getMainViewModel(): MainViewModel {
        val repository = getMainRepository()
        return MainViewModel(repository)
    }

}