package ru.zaytsev.githubtest

import okhttp3.Interceptor
import okhttp3.Response
import ru.zaytsev.githubtest.networking.Token

class TokenInterceptor(): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val modidiedRequest = chain.request().newBuilder()
            .addHeader("Authorization", Token.TOKEN)
            .build()
        return chain.proceed(modidiedRequest)
    }
}