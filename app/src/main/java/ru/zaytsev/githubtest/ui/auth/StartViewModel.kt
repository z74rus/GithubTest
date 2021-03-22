package ru.zaytsev.githubtest.ui.auth

import android.app.Application
import android.content.Intent
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import net.openid.appauth.AuthorizationException
import net.openid.appauth.AuthorizationService
import net.openid.appauth.TokenRequest
import ru.zaytsev.githubtest.data.AuthRepository
import ru.zaytsev.githubtest.R
import ru.zaytsev.githubtest.utils.SingleLiveEvent


class StartViewModel(application: Application): AndroidViewModel(application) {
    private val authRepository = AuthRepository()
    private val authService: AuthorizationService = AuthorizationService(getApplication())
    private val openAuthPageLiveEvent = SingleLiveEvent<Intent>()
    private val toastLiveEvent = SingleLiveEvent<Int>()
    private val loadingMutableLiveData = MutableLiveData(false)
    private val authSuccessLiveEvent = SingleLiveEvent<Unit>()

    val openAuthPageLiveData: LiveData<Intent>
        get() = openAuthPageLiveEvent
    val loadingLiveData: LiveData<Boolean>
        get() = loadingMutableLiveData
    val toastLiveData: LiveData<Int>
        get() = toastLiveEvent
    val authSuccessLiveData: LiveData<Unit>
        get() = authSuccessLiveEvent

    fun onAuthCodeFailed(exception: AuthorizationException) {
        toastLiveEvent.postValue(R.string.auth_canceled)
    }

    fun onAuthCodeReceived(tokenRequest: TokenRequest) {
        loadingMutableLiveData.postValue(true)
        authRepository.performTokenRequest(
            authService = authService,
            tokenRequest = tokenRequest,
            onComplete = {
                loadingMutableLiveData.postValue(false)
                authSuccessLiveEvent.postValue(Unit)
            },
            onError = {
                loadingMutableLiveData.postValue(false)
                toastLiveEvent.postValue(R.string.auth_canceled)
            }
        )
    }

    fun openLoginPage() {
        val customTableIntent = CustomTabsIntent.Builder()
            .setToolbarColor(ContextCompat.getColor(getApplication(), R.color.black))
            .build()
        val openAuthPageIntent = authService.getAuthorizationRequestIntent(
            authRepository.getAuthRequest(),
            customTableIntent
        )

        openAuthPageLiveEvent.postValue(openAuthPageIntent)

    }

    override fun onCleared() {
        super.onCleared()
        authService.dispose()
    }
}