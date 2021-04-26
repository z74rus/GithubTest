package com.example.githubconductor.mvp.models

import android.util.Log
import com.example.githubconductor.data.network.GithubApi
import com.example.githubconductor.models.Repo
import com.example.githubconductor.models.DetailUser
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