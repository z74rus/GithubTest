package com.example.githubmvp.di.module

import android.app.Application
import androidx.room.Room
import com.example.githubmvp.data.database.UserDao
import com.example.githubmvp.data.database.UserDataBase
import dagger.Module
import dagger.Provides
import dagger.Reusable


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