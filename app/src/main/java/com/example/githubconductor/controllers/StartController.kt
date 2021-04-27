package com.example.githubconductor.controllers

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bluelinelabs.conductor.RouterTransaction
import com.example.githubconductor.databinding.ControllerStartBinding
import com.example.githubconductor.di.GithubApp
import com.example.githubconductor.mvp.presenters.StartPresenter
import com.example.githubconductor.mvp.views.StartView
import com.example.githubconductor.utils.MoxyController
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import net.openid.appauth.AuthorizationException
import net.openid.appauth.AuthorizationResponse
import javax.inject.Inject

class StartController : MoxyController(), StartView {

    private var _binding: ControllerStartBinding? = null
    private val binding get() = _binding!!

    @Inject
    @InjectPresenter
    lateinit var presenter: StartPresenter

    @ProvidePresenter
    fun provideMainPresenter() = presenter


    private fun initUI() {
        presenter.checkIsToken()
        binding.continueButton.setOnClickListener {
            presenter.startWithoutLogin()
        }
        binding.signInButton.setOnClickListener {
            presenter.openLoginPage()
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup,
        savedViewState: Bundle?
    ): View {
        _binding = ControllerStartBinding.inflate(inflater, container, false)
        GithubApp.mainComponent.inject(this)
        initUI()
        return binding.root
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
        router.pushController(RouterTransaction.with(MainController()))
    }

    override fun isLoading(isLoading: Boolean) {
        binding.signInButton.isEnabled = !isLoading
        binding.continueButton.isEnabled = !isLoading

    }

    override fun onClickButtonWithoutLogin() {
        router.pushController(RouterTransaction.with(SearchUsersController()))
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    companion object {
        private const val AUTH_REQUEST_CODE = 342
    }

}