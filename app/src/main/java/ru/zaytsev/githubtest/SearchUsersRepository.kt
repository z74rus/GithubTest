package ru.zaytsev.githubtest

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.zaytsev.githubtest.networking.Network


class SearchUsersRepository {
    suspend fun searchUsers(
        query: String
    ): List<User> {
        return withContext(Dispatchers.IO) {
            Network.gitHubApi.searchUsers(query).items
        }
    }
}