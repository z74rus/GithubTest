package com.example.githubmvp.mvp.models

import android.util.Log
import com.example.githubmvp.data.network.GithubApi
import com.example.githubmvp.models.Repo
import com.example.githubmvp.models.DetailUser
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
            Log.d("ERRORPUK", "userName = $userName")
            githubApi.getCurrentUser(userName)
        } catch (t: Throwable) {
            Log.e("ERRORPUK", t.message.toString())
            null
        }
    }


}