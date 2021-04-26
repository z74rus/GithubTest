package com.example.githubconductor.data.network

import net.openid.appauth.ResponseTypeValues

object AuthConfig {
    const val AUTH_URI = "https://github.com/login/oauth/authorize"
    const val TOKEN_URI = "https://github.com/login/oauth/access_token"
    const val RESPONSE_TYPE = ResponseTypeValues.CODE
    const val SCOPE = "user"

    const val CLIENT_ID = "0a0bb4c72e2e7d163ce2"
    const val CLIENT_SECRET = "1c9baa76a9d9679dbcb817f65d1a94bc6c90a714"
    const val CALLBACK_URL = "kukambek://kukambek.com/callback"
    var accessToken: String? = null

}