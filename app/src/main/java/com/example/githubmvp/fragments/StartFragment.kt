package com.example.githubmvp.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.githubmvp.R
import com.example.githubmvp.data.network.AuthConfig
import com.example.githubmvp.databinding.FragmentStartBinding
import com.example.githubmvp.di.GithubApp
import com.example.githubmvp.mvp.presenters.StartPresenter
import com.example.githubmvp.mvp.views.StartView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import net.openid.appauth.AuthorizationException
import net.openid.appauth.AuthorizationResponse
import javax.inject.Inject

class StartFragment : MvpAppCompatFragment(), StartView {

    private var _binding: FragmentStartBinding? = null
    private val binding get() = _binding!!

    private val sharedPref by lazy {
        requireContext().getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
    }
    private var token: String? = null

    lateinit var startPresenter: StartPresenter
    private val presenter: StartPresenter by moxyPresenter { startPresenter }

    override fun onAttach(context: Context) {
        startPresenter = StartPresenter(requireActivity().application)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStartBinding.inflate(inflater, container, false)
        initUI()
        isLogOut()
        isSingInCheck()
        return binding.root
    }

    private fun initUI() {
        with(binding) {
            continueButton.setOnClickListener {
                presenter.startWithoutLogin()
            }
            signInButton.setOnClickListener {
                presenter.openLoginPage()
            }
        }
    }

    private fun isLogOut() {
        if (isLogOut) {
            sharedPref.edit()
                .clear().apply()
        }
    }

    private fun isSingInCheck() {
        CoroutineScope(Dispatchers.IO).launch {
            token = sharedPref.getString(TOKEN, null)
            AuthConfig.accessToken = token
            if (token != null) {
                launch(Dispatchers.Main) {
                    requireFragmentManager().beginTransaction()
                        .replace(R.id.parentContainer, MainFragment())
                        .commit()
                }
            }
        }

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
        Toast.makeText(requireContext(), "Error - $message", Toast.LENGTH_SHORT).show()
    }

    override fun openLoginPage(intent: Intent) {
        startActivityForResult(intent, AUTH_REQUEST_CODE)
    }

    override fun onSuccess(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onSuccessLogin() {
        CoroutineScope(Dispatchers.IO).launch {
            sharedPref.edit()
                .putString(TOKEN, AuthConfig.accessToken)
                .apply()
            launch(Dispatchers.Main) {
                requireFragmentManager().beginTransaction()
                    .replace(R.id.container, MainFragment())
            }
        }

    }

    override fun isLoading(isLoading: Boolean) {
        binding.signInButton.isEnabled = !isLoading
        binding.continueButton.isEnabled = !isLoading
    }

    override fun onClickButtonWithoutLogin() {
        requireFragmentManager().beginTransaction()
            .replace(R.id.parentContainer, SearchUsersFragment())
            .addToBackStack("FromStartToSearchUsersFragment")
            .commit()
    }

    companion object {
        private const val AUTH_REQUEST_CODE = 342
        private const val SHARED_PREF = "SHARED_PREF"
        private const val TOKEN = "TOKEN"
        var isLogOut = false
    }

}