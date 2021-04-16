package ru.zaytsev.githubtest.data

import android.util.Log
import ru.zaytsev.githubtest.models.DetailUser
import ru.zaytsev.githubtest.models.Repo
import ru.zaytsev.githubtest.networking.GithubApi
import javax.inject.Inject

class UserDetailRepo @Inject constructor(
    private val githubApi: GithubApi
) {

    suspend fun getRepos(userName: String): List<Repo> {
        return try {
            githubApi.getRepos(userName)
        } catch (t: Throwable) {
            Log.e("ERROR", t.message.toString())
            emptyList()
        }
    }

    suspend fun getUserDetail(userName: String): DetailUser? {
        return try {
            githubApi.getCurrentUser(userName)
        } catch (t: Throwable) {
            Log.e("ERROR", t.message.toString())
            null
        }
    }


}