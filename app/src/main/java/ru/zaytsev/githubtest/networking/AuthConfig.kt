package ru.zaytsev.githubtest.networking

import net.openid.appauth.ResponseTypeValues

object AuthConfig {
    const val AUTH_URI = "https://github.com/login/oauth/authorize"
    const val TOKEN_URI = "https://github.com/login/oauth/access_token"
    const val RESPONSE_TYPE = ResponseTypeValues.CODE
    const val SCOPE = "user"

    const val CLIENT_ID = "0a0bb4c72e2e7d163ce2"
    const val CLIENT_SECRET = "3cc0a6121b3465556c8cbd28c28158f1f598a6b9"
    const val CALLBACK_URL = "kukambek://kukambek.com/callback"
    var accessToken: String? = null

}