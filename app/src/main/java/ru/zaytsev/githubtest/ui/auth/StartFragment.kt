package ru.zaytsev.githubtest.ui.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.openid.appauth.AuthorizationException
import net.openid.appauth.AuthorizationResponse
import ru.zaytsev.githubtest.databinding.FragmentStartBinding
import ru.zaytsev.githubtest.networking.AuthConfig
import ru.zaytsev.githubtest.utils.toast

class StartFragment : Fragment() {


    private var _binding: FragmentStartBinding? = null
    private val binding get() = _binding!!

    private val viewModel: StartViewModel by viewModels()
    private val sharedPref by lazy {
        requireContext().getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
    }
    private var token: String? = null



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
                findNavController().navigate(StartFragmentDirections.actionStartFragmentToSearchUsersFragment())
            }
            signInButton.setOnClickListener {
                viewModel.openLoginPage()
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
        lifecycleScope.launch(Dispatchers.IO) {
            token = sharedPref.getString(TOKEN, null)
            AuthConfig.accessToken = token
            if (token != null) {
                launch(Dispatchers.Main) {
                    findNavController().navigate(StartFragmentDirections.actionStartFragmentToMainFragment())
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
                    viewModel.onAuthCodeReceived(tokenExchangeRequest)
                exception != null -> viewModel.onAuthCodeFailed(exception)
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun bindViewModel() {
        binding.signInButton.setOnClickListener {
            viewModel.openLoginPage()
        }
        viewModel.loadingLiveData.observe(viewLifecycleOwner, ::updateIsLoading)
        viewModel.openAuthPageLiveData.observe(viewLifecycleOwner, ::openAuthPage)
        viewModel.toastLiveData.observe(viewLifecycleOwner, ::toast)
        viewModel.authSuccessLiveData.observe(viewLifecycleOwner) {
            lifecycleScope.launch(Dispatchers.IO) {
                sharedPref.edit()
                    .putString(TOKEN, AuthConfig.accessToken)
                    .apply()
                launch(Dispatchers.Main) { findNavController().navigate(StartFragmentDirections.actionStartFragmentToMainFragment()) }

            }
        }

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        bindViewModel()
    }

    private fun openAuthPage(intent: Intent) {
        startActivityForResult(intent, AUTH_REQUEST_CODE)
    }

    private fun updateIsLoading(isLoading: Boolean) {
        binding.signInButton.isEnabled = !isLoading
        binding.continueButton.isEnabled = !isLoading
    }

    companion object {
        private const val AUTH_REQUEST_CODE = 342
        private const val SHARED_PREF = "SHARED_PREF"
        private const val TOKEN = "TOKEN"
        var isLogOut = false
    }


}