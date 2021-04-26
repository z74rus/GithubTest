package com.example.githubconductor.mvp.models

import com.example.githubconductor.data.network.GithubApi
import com.example.githubconductor.models.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class SearchUsersRepository @Inject constructor(
    private val githubApi: GithubApi
) {
    suspend fun searchUsers(
        query: String
    ): List<User> {
        return withContext(Dispatchers.IO) {
            try {
                githubApi.searchUsers(query = query).items
            } catch (t: Throwable) {
                emptyList<User>()
            }
        }
    }

    suspend fun getFollowers(
        followers_url: String
    ): List<User> {
        return withContext(Dispatchers.IO) {
            try {
                githubApi.getFollowers(followers_url)
            } catch (t: Throwable) {
                emptyList<User>()
            }
        }
    }
}