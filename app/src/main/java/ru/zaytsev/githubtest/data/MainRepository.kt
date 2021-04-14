package ru.zaytsev.githubtest.data

import ru.zaytsev.githubtest.database.DataBase
import ru.zaytsev.githubtest.models.DetailUser
import ru.zaytsev.githubtest.networking.Network

class MainRepository {

    private val userDao = DataBase.instance.userDao()

    suspend fun getUserInfo(): DetailUser? {
        return try {
            val user = Network.gitHubApi.getUser()
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