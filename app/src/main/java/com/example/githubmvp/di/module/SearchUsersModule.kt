package com.example.githubmvp.di.module

import com.example.githubmvp.data.network.GithubApi
import com.example.githubmvp.mvp.models.SearchUsersRepository
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