package ru.zaytsev.githubtest.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.zaytsev.githubtest.models.User
import ru.zaytsev.githubtest.networking.Network


class SearchUsersRepository {
    suspend fun searchUsers(
        query: String
    ): List<User> {
        return withContext(Dispatchers.IO) {
            try {
                Network.gitHubApi.searchUsers(query = query).items
            } catch (t: Throwable) {
                emptyList()
            }
        }
    }

    suspend fun getFollowers(
        followers_url: String
    ): List<User> {
        return withContext(Dispatchers.IO) {
            try {
                Network.gitHubApi.getFollowers(followers_url)
            } catch (t: Throwable) {
                emptyList()
            }
        }
    }
}