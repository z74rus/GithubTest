package com.example.githubconductor.di.module

import com.example.githubconductor.data.network.GithubApi
import com.example.githubconductor.mvp.models.MainRepository
import com.example.githubmvp.data.database.UserDao
import dagger.Module
import dagger.Provides
import dagger.Reusable


@Module
class MainModule {
    @Reusable
    @Provides
    fun provideMainRepo(githubApi: GithubApi, userDao: UserDao): MainRepository {
        return MainRepository(userDao, githubApi)
    }
}