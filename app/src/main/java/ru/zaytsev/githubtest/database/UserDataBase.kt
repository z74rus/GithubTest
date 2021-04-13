package ru.zaytsev.githubtest.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.zaytsev.githubtest.models.DetailUser

@Database(entities = [DetailUser::class], version = 1)
abstract class UserDataBase: RoomDatabase() {
    abstract fun userDao(): UserDao
}