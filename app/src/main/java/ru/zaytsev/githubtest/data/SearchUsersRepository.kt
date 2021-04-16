package ru.zaytsev.githubtest.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.zaytsev.githubtest.models.User
import ru.zaytsev.githubtest.networking.GithubApi
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