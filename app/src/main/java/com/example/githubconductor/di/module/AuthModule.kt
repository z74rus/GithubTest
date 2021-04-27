package com.example.githubconductor.di.module

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.Reusable
import net.openid.appauth.AuthorizationService
@Module
class AuthModule {
    @Reusable
    @Provides
    fun provideAuth(context: Context) =  AuthorizationService(context)
}