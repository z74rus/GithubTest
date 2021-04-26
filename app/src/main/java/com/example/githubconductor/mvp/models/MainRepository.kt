package com.example.githubconductor.mvp.models

import com.example.githubmvp.data.database.UserDao
import com.example.githubconductor.data.network.GithubApi
import com.example.githubconductor.models.DetailUser
import javax.inject.Inject


class MainRepository @Inject constructor(
    private val userDao: UserDao,
    private val githubApi: GithubApi
) {

    suspend fun getUserInfo(): DetailUser? {
        return try {
            val user = githubApi.getUser()
            saveUser(user)
            getUserById(user.id)
        } catch (t: Throwable) {
            getUsers().first()
        }
    }

    private suspend fun saveUser(user: DetailUser) {
        try {
            userDao.loadDetailUser(user)
        } catch (t: Throwable) {
            t.printStackTrace()
        }
    }

    private suspend fun getUserById(userId: Long): DetailUser? {
        return try {
            userDao.getDetailUserById(userId)
        } catch (t: Throwable) {
            t.printStackTrace()
            null
        }

    }

    private suspend fun getUsers(): List<DetailUser> {
        return try {
            userDao.getUsers()
        } catch (t: Throwable) {
            emptyList()
        }
    }


}