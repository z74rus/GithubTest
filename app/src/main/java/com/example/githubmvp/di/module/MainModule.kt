package com.example.githubmvp.di.module

import com.example.githubmvp.data.database.UserDao
import com.example.githubmvp.data.network.GithubApi
import com.example.githubmvp.mvp.models.MainRepository
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