package com.example.githubmvp.data.network

import okhttp3.Interceptor
import okhttp3.Response

class TokenInterceptor(private val token: String?): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val neededToken = token ?: Token.TOKEN
        val modifiedRequest = chain.request().newBuilder()
            .addHeader("Authorization", "token $neededToken")
            .addHeader("Accept", "application/vnd.github.v3+json")
            .build()
        return chain.proceed(modifiedRequest)
    }
}