package com.example.githubconductor.di.module

import android.app.Application
import dagger.Module
import dagger.Provides
import dagger.Reusable
import net.openid.appauth.AuthorizationService
@Module
class AuthModule(private val application: Application) {
    @Reusable
    @Provides
    fun provideAuth() =  AuthorizationService(application)
}