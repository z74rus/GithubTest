package ru.zaytsev.githubtest

import android.util.Log
import ru.zaytsev.githubtest.networking.Network


class UserDetailRepo {

    suspend fun getRepos(userName: String): List<Repo> {
        Log.d("ERROR", "INTO getRepos UserDetailRepo")
        val items = Network.gitHubApi.getRepos(userName)
        Log.d("ERROR", "UserDetailRepo getRepos response list = $items")
        return items
    }

    suspend fun getUserDetail(userName: String): DetailUser {
        Log.d("ERROR", "INTO getUserDetail UserDetailRepo")
        val user = Network.gitHubApi.getUser(userName)
        Log.d("ERROR", "UserDetailRepo getDetailUser response user = $user")
        return user
    }
}