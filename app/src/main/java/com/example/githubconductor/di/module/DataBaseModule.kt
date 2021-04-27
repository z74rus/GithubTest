package com.example.githubconductor.di.module

import android.content.Context
import androidx.room.Room
import com.example.githubmvp.data.database.UserDao
import com.example.githubmvp.data.database.UserDataBase
import dagger.Module
import dagger.Provides
import dagger.Reusable


@Module
class DataBaseModule {
    @Reusable
    @Provides
    fun provideDataBase(context: Context): UserDataBase {
        return Room.databaseBuilder(
            context,
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