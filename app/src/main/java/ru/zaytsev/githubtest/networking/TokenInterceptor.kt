package ru.zaytsev.githubtest.networking

import okhttp3.Interceptor
import okhttp3.Response
import ru.zaytsev.githubtest.networking.Token

class TokenInterceptor(private val token: String?): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val neededToken = token ?: Token.TOKEN
        val modidiedRequest = chain.request().newBuilder()
            .addHeader("Authorization", "token $neededToken")
            .addHeader("Accept", "application/vnd.github.v3+json")
            .build()
        return chain.proceed(modidiedRequest)
    }
}