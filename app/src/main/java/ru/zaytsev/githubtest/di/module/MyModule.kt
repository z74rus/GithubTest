package ru.zaytsev.githubtest.di.module

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import ru.zaytsev.githubtest.di.ViewModelFactory

@Module
abstract class MyModule {
    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}