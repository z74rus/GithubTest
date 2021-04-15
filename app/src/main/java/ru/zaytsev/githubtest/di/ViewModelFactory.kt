package ru.zaytsev.githubtest.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.zaytsev.githubtest.ui.main.MainViewModel
import javax.inject.Inject
import javax.inject.Provider

class ViewModelFactory @Inject constructor(
    private val myViewModelProvider: Provider<MainViewModel>
): ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return myViewModelProvider.get() as T
    }
}

