package com.example.githubconductor.di.module

import android.app.Application
import android.content.Intent
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import com.example.githubconductor.R
import dagger.Module
import dagger.Provides
import dagger.Reusable

@Module
class TabsIntentModule(private val application: Application) {
    @Reusable
    @Provides
    fun provideIntent(): CustomTabsIntent {
        return CustomTabsIntent.Builder()
            .setToolbarColor(ContextCompat.getColor(application, R.color.black))
            .build()
    }
}