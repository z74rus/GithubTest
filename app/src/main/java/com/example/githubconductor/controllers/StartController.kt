package com.example.githubconductor.controllers

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.bluelinelabs.conductor.RouterTransaction
import com.example.githubconductor.R
import com.example.githubconductor.data.network.AuthConfig
import com.example.githubconductor.mvp.presenters.StartPresenter
import com.example.githubconductor.mvp.views.StartView
import com.example.githubconductor.utils.MoxyController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import moxy.ktx.moxyPresenter
import net.openid.appauth.AuthorizationException
import net.openid.appauth.AuthorizationResponse

class StartController: MoxyController(), StartView {

    private lateinit var continueButton: Button
    private lateinit var signInButton: Button

    private val sharedPref by lazy {
        activity?.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
    }
    private var token: String? = null

    lateinit var startPresenter: StartPresenter
    private val presenter: StartPresenter by moxyPresenter { startPresenter }


    private fun initUI() {
            continueButton.setOnClickListener {
                presenter.startWithoutLogin()
            }
            signInButton.setOnClickListener {
                presenter.openLoginPage()
            }
    }

    private fun isLogOut() {
        if (isLogOut) {
            sharedPref
                ?.edit()
                ?.clear()
                ?.apply()
        }
    }

    private fun isSingInCheck() {
        CoroutineScope(Dispatchers.IO).launch {
            token = sharedPref?.getString(TOKEN, null)
            AuthConfig.accessToken = token
            if (token != null) {
                launch(Dispatchers.Main) {
                    router.pushController(RouterTransaction.with(MainController()))
                }
            }
        }

    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup,
        savedViewState: Bundle?
    ): View = inflater.inflate(R.layout.controller_start, container, false).apply {
        continueButton = findViewById(R.id.continueButton)
        signInButton = findViewById(R.id.signInButton)
        startPresenter = StartPresenter(activity!!.application,)
        initUI()
        isSingInCheck()
        isLogOut()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == AUTH_REQUEST_CODE && data != null) {
            val tokenExchangeRequest = AuthorizationResponse.fromIntent(data)
                ?.createTokenExchangeRequest()
            val exception = AuthorizationException.fromIntent(data)
            when {
                tokenExchangeRequest != null && exception == null ->
                    presenter.onAuthCodeReceived(tokenExchangeRequest)
                exception != null -> presenter.onAuthCodeFailed(exception = exception)
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun onError(message: String) {
        Toast.makeText(activity, "Error - $message", Toast.LENGTH_SHORT).show()
    }

    override fun openLoginPage(intent: Intent) {
        startActivityForResult(intent, AUTH_REQUEST_CODE)
    }

    override fun onSuccess(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }

    override fun onSuccessLogin() {
        GlobalScope.launch(Dispatchers.IO) {
            sharedPref?.edit()
                ?.putString(TOKEN, AuthConfig.accessToken)
                ?.apply()
            launch(Dispatchers.Main) {
                router.pushController(RouterTransaction.with(MainController()))
            }
        }

    }

    override fun isLoading(isLoading: Boolean) {
        signInButton.isEnabled = !isLoading
        continueButton.isEnabled = !isLoading

    }

    override fun onClickButtonWithoutLogin() {
        router.pushController(RouterTransaction.with(SearchUsersController()))
    }


    companion object {
        private const val AUTH_REQUEST_CODE = 342
        private const val SHARED_PREF = "SHARED_PREF"
        private const val TOKEN = "TOKEN"
        var isLogOut = false
    }

}