package com.example.githubconductor.di.component

import android.content.SharedPreferences
import com.example.githubconductor.controllers.*
import com.example.githubconductor.di.module.*
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [DataBaseModule::class, NetworkModule::class, SharedPrefModule::class, AuthModule::class, TabsIntentModule::class])
interface MainComponent {
    fun inject(startController: StartController)
    fun inject(mainController: MainController)
    fun inject(userDetailController: UserDetailController)
    fun inject(searchUsersController: SearchUsersController)
    fun inject(editUserInfoController: EditUserInfoController)
}