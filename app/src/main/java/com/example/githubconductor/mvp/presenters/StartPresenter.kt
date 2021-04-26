package com.example.githubconductor.mvp.presenters

import android.app.Application
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import com.example.githubconductor.R
import com.example.githubconductor.mvp.models.AuthRepository
import com.example.githubconductor.mvp.views.StartView
import moxy.InjectViewState
import moxy.MvpPresenter
import net.openid.appauth.AuthorizationException
import net.openid.appauth.AuthorizationService
import net.openid.appauth.TokenRequest

@InjectViewState
class StartPresenter(
    private val application: Application
) : MvpPresenter<StartView>() {
    private val authRepository: AuthRepository = AuthRepository()

    private val authService: AuthorizationService = AuthorizationService(application)

    fun onAuthCodeFailed(exception: AuthorizationException) {
        viewState.onError(exception.message.toString())
    }

    fun onAuthCodeReceived(tokenRequest: TokenRequest) {
        viewState.isLoading(true)
        authRepository.performTokenRequest(
            authService = authService,
            tokenRequest = tokenRequest,
            onComplete = {
                viewState.isLoading(false)
                viewState.onSuccessLogin()
            },
            onError = {
                viewState.isLoading(false)
                viewState.onError("Error")
            }
        )
    }

    fun openLoginPage() {
        val customTableIntent = CustomTabsIntent.Builder()
            .setToolbarColor(ContextCompat.getColor(application, R.color.black))
            .build()
        val openAuthPageIntent = authService.getAuthorizationRequestIntent(
            authRepository.getAuthRequest(),
            customTableIntent
        )
        viewState.openLoginPage(openAuthPageIntent)
    }

    fun startWithoutLogin() {
        viewState.onClickButtonWithoutLogin()
    }
}