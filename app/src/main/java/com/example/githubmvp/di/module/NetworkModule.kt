package com.example.githubmvp.di.module


import com.example.githubmvp.data.network.AuthConfig
import com.example.githubmvp.data.network.GithubApi
import com.example.githubmvp.data.network.TokenInterceptor
import dagger.Module
import dagger.Provides
import dagger.Reusable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
class NetworkModule {

    @Provides
    @Reusable
    internal fun provideOkHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val tokenInterceptor = TokenInterceptor(AuthConfig.accessToken)

        return OkHttpClient.Builder()
            .addNetworkInterceptor(interceptor)
            .addNetworkInterceptor(tokenInterceptor)
            .build()
    }
    @Provides
    @Reusable
    internal fun provideRetrofitInterface(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl("https://api.github.com/")
        .addConverterFactory(MoshiConverterFactory.create())
        .client(okHttpClient)
        .build()

    @Provides
    @Reusable
    internal fun provideGitHubApi(retrofit: Retrofit): GithubApi = retrofit.create(GithubApi::class.java)
}