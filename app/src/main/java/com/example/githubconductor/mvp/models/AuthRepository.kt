package com.example.githubconductor.mvp.models

import android.content.SharedPreferences
import android.net.Uri
import android.util.Log
import com.example.githubconductor.data.network.AuthConfig
import net.openid.appauth.AuthorizationRequest
import net.openid.appauth.AuthorizationService
import net.openid.appauth.AuthorizationServiceConfiguration
import net.openid.appauth.ClientAuthentication
import net.openid.appauth.ClientSecretPost
import net.openid.appauth.TokenRequest
import javax.inject.Inject


class AuthRepository @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {

    private fun onLoginSuccess(token: String) {
        sharedPreferences.edit()
            .putString(TOKEN_PREF, token)
            .apply()
    }

    fun checkIsToken(): Boolean {
        return sharedPreferences.getString(TOKEN_PREF, null) != null
    }


    fun getAuthRequest(): AuthorizationRequest {
        val serviceConfiguration = AuthorizationServiceConfiguration(
            Uri.parse(AuthConfig.AUTH_URI),
            Uri.parse(AuthConfig.TOKEN_URI)
        )

        val redirectUri = Uri.parse(AuthConfig.CALLBACK_URL)

        return AuthorizationRequest.Builder(
            serviceConfiguration,
            AuthConfig.CLIENT_ID,
            AuthConfig.RESPONSE_TYPE,
            redirectUri
        )
            .setScope(AuthConfig.SCOPE)
            .build()
    }

    fun performTokenRequest(
        authService: AuthorizationService,
        tokenRequest: TokenRequest,
        onComplete: () -> Unit,
        onError: () -> Unit
    ) {
        authService.performTokenRequest(tokenRequest, getClientAuthentication()) { response, ex ->
            when {
                response != null -> {
                    Log.d("TOKEN", "token = ${response.accessToken}")
                    val accessToken = response.accessToken.orEmpty()
                    onLoginSuccess(accessToken)
                    onComplete()
                }
                else -> onError()
            }
        }
    }

    private fun getClientAuthentication(): ClientAuthentication {
        return ClientSecretPost(AuthConfig.CLIENT_SECRET)
    }

    companion object {
        private const val TOKEN_PREF = "TOKEN"
    }
}