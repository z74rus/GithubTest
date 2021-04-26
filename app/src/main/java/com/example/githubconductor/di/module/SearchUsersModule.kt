package com.example.githubconductor.di.module

import com.example.githubconductor.data.network.GithubApi
import com.example.githubconductor.mvp.models.SearchUsersRepository
import dagger.Module
import dagger.Provides
import dagger.Reusable

@Module
class SearchUsersModule {
    @Reusable
    @Provides
    fun provideSearchRepo(githubApi: GithubApi): SearchUsersRepository {
        return SearchUsersRepository(githubApi = githubApi)
    }
}