package ru.zaytsev.githubtest.data

import android.util.Log
import ru.zaytsev.githubtest.models.DetailUser
import ru.zaytsev.githubtest.networking.Network

class MainRepository {
    suspend fun getUserInfo(): DetailUser {
        return try {
            Network.gitHubApi.getUser()
        } catch (t: Throwable) {
            Log.e("ERROR","Error at MainRepository fun getUsersInfo: ${t.message}")
            DetailUser()
        }
    }
}