package ru.zaytsev.githubtest.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.zaytsev.githubtest.database.UserDataBase.Companion.DB_VERSION
import ru.zaytsev.githubtest.models.DetailUser

@Database(
    entities = [DetailUser::class],
    version = DB_VERSION
)
abstract class UserDataBase: RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        const val DB_VERSION = 1
        const val DB_NAME = "current_user_database"
    }
}