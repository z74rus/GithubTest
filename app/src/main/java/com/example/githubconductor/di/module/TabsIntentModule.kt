package com.example.githubconductor.di.module

import android.content.Context
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import com.example.githubconductor.R
import dagger.Module
import dagger.Provides
import dagger.Reusable

@Module
class TabsIntentModule {
    @Reusable
    @Provides
    fun provideIntent(context: Context): CustomTabsIntent {
        return CustomTabsIntent.Builder()
            .setToolbarColor(ContextCompat.getColor(context, R.color.black))
            .build()
    }
}