package com.example.githubmvp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.githubmvp.data.database.UserDataBase.Companion.DB_VERSION
import com.example.githubmvp.models.DetailUser

@Database(
    entities = [DetailUser::class],
    version = DB_VERSION,
    exportSchema = false
)
abstract class UserDataBase: RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        const val DB_VERSION = 1
        const val DB_NAME = "current_user_database"
    }
}