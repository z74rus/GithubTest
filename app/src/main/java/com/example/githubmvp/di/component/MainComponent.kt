package com.example.githubmvp.di.component

import com.example.githubmvp.di.module.DataBaseModule
import com.example.githubmvp.di.module.MainModule
import com.example.githubmvp.di.module.NetworkModule
import com.example.githubmvp.fragments.*
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [DataBaseModule::class, NetworkModule::class, MainModule::class])
interface MainComponent {
    fun inject(mainFragment: MainFragment)
    fun inject(userDetailFragment: UserDetailFragment)
    fun inject(searchUsersFragment: SearchUsersFragment)
    fun inject(editUserInfoFragment: EditUserInfoFragment)
}