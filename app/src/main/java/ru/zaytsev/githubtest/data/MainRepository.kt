package ru.zaytsev.githubtest.data

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.room.Room
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import ru.zaytsev.githubtest.database.UserDataBase
import ru.zaytsev.githubtest.models.DetailUser
import ru.zaytsev.githubtest.models.User
import ru.zaytsev.githubtest.networking.Network
import java.lang.IllegalStateException
import java.util.concurrent.Executors

private const val DATA_BASE = "DATA_BASE"

class MainRepository(context: Context) {

    companion object {
        private var INSTANCE: MainRepository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = MainRepository(context)
            }
        }

        fun get(): MainRepository {
            return INSTANCE ?: throw IllegalStateException("MainRepository must be initialized")
        }
    }

    suspend fun getUserInfo(): DetailUser? {
        return try {
            val remoteUser = Network.gitHubApi.getUser()
            userDao.loadDetailUser(remoteUser)
            return remoteUser

        } catch (t: Throwable) {
            Log.e("ERROR", "Error at MainRepository fun getUsersInfo: ${t.message}")
            null
        }
    }

    private val executor = Executors.newSingleThreadExecutor()

    private val database: UserDataBase = Room.databaseBuilder(
        context.applicationContext,
        UserDataBase::class.java,
        DATA_BASE
    ).build()

    private val userDao = database.userDao()

    fun getUserInfoFromDatabase(id: Long): LiveData<DetailUser?> =
        userDao.getDetailUser(id)

    fun updateUserInfo(user: DetailUser?) {
        executor.execute {
            userDao.updateDetailUser(user)
        }
    }

    fun loadDetailUserInfo(user: DetailUser?) {
        executor.execute {
            userDao.loadDetailUser(user)
        }
    }

    fun getDetailUsers(): LiveData<List<DetailUser>?> {
        return userDao.getUsers()
    }
}