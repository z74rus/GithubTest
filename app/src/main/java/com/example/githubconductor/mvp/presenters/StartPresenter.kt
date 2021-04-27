package com.example.githubconductor.mvp.presenters

import androidx.browser.customtabs.CustomTabsIntent
import com.example.githubconductor.mvp.models.AuthRepository
import com.example.githubconductor.mvp.views.StartView
import moxy.InjectViewState
import moxy.MvpPresenter
import net.openid.appauth.AuthorizationException
import net.openid.appauth.AuthorizationService
import net.openid.appauth.TokenRequest
import javax.inject.Inject

@InjectViewState
class StartPresenter @Inject constructor(
    private val customTabsIntent: CustomTabsIntent,
    private val authRepository: AuthRepository,
    private val authService: AuthorizationService
) : MvpPresenter<StartView>() {

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

    fun checkIsToken() {
        if(authRepository.checkIsToken())
            viewState.onSuccessLogin()
    }

    fun openLoginPage() {
        if (authRepository.checkIsToken()) {
            viewState.onSuccessLogin()
        } else {
            val openAuthPageIntent = authService.getAuthorizationRequestIntent(
                authRepository.getAuthRequest(),
                customTabsIntent
            )
            viewState.openLoginPage(openAuthPageIntent)
        }
    }

    fun startWithoutLogin() {
        viewState.onClickButtonWithoutLogin()
    }
}