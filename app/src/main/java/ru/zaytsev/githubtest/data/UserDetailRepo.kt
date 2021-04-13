package ru.zaytsev.githubtest.data

import android.util.Log
import ru.zaytsev.githubtest.models.DetailUser
import ru.zaytsev.githubtest.models.Repo
import ru.zaytsev.githubtest.networking.Network


class UserDetailRepo {

    suspend fun getRepos(userName: String): List<Repo> {
        return try {
            Network.gitHubApi.getRepos(userName)
        } catch (t: Throwable) {
            Log.e("ERROR", t.message.toString())
            emptyList()
        }
    }

    suspend fun getUserDetail(userName: String): DetailUser? {
        return try {
            Network.gitHubApi.getCurrentUser(userName)
        } catch (t: Throwable) {
            Log.e("ERROR", t.message.toString())
            null
        }
    }


}