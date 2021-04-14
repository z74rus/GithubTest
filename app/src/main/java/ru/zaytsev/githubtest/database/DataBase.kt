package ru.zaytsev.githubtest.database

import android.content.Context
import androidx.room.Room
import ru.zaytsev.githubtest.database.UserDataBase.Companion.DB_NAME


object DataBase {
    lateinit var instance: UserDataBase
        private set

    fun init(context: Context) {
        instance = Room.databaseBuilder(
            context,
            UserDataBase::class.java,
            DB_NAME
        )
            .build()
    }
}