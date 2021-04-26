package com.example.githubconductor.di.component

import com.example.githubconductor.controllers.EditUserInfoController
import com.example.githubconductor.controllers.MainController
import com.example.githubconductor.controllers.SearchUsersController
import com.example.githubconductor.controllers.UserDetailController
import com.example.githubconductor.di.module.DataBaseModule
import com.example.githubconductor.di.module.MainModule
import com.example.githubconductor.di.module.NetworkModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [DataBaseModule::class, NetworkModule::class, MainModule::class])
interface MainComponent {
    fun inject(mainController: MainController)
    fun inject(userDetailController: UserDetailController)
    fun inject(searchUsersController: SearchUsersController)
    fun inject(editUserInfoController: EditUserInfoController)
}