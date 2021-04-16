package ru.zaytsev.githubtest.di.module

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.Reusable
import ru.zaytsev.githubtest.database.UserDao
import ru.zaytsev.githubtest.database.UserDataBase

@Module
class DataBaseModule(private val application: Application) {
    @Reusable
    @Provides
    fun provideDataBase(): UserDataBase {
        return Room.databaseBuilder(
            application,
            UserDataBase::class.java,
            UserDataBase.DB_NAME
        )
            .build()
    }
    @Reusable
    @Provides
    fun provideUserDao(db: UserDataBase): UserDao {
        return db.userDao()
    }

}