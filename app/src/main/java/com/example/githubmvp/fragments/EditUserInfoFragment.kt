package com.example.githubmvp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.githubmvp.di.GithubApp
import com.example.githubmvp.databinding.FragmentEditUserInfoBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import moxy.MvpAppCompatFragment
import com.example.githubmvp.models.EditUser
import com.example.githubmvp.mvp.presenters.EditUserInfoPresenter
import com.example.githubmvp.mvp.views.EditUserView
import moxy.InjectViewState
import moxy.ktx.moxyPresenter
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject

class EditUserInfoFragment: MvpAppCompatFragment(), EditUserView {

    private var _binding: FragmentEditUserInfoBinding? = null
    private val binding get() = _binding!!

    @Inject
    @InjectPresenter
    lateinit var editUserInfoPresenter: EditUserInfoPresenter

    @ProvidePresenter
    fun provideEditPresenter() = editUserInfoPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        GithubApp.mainComponent.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditUserInfoBinding.inflate(inflater, container, false)

        binding.acceptButton.setOnClickListener {
            updateInfo()
        }

        return binding.root
    }

    private fun updateInfo() {
        val name = binding.nameEditText.text.toString()
        val location = binding.locationEditText.text.toString()
        val bio = binding.bioEditText.text.toString()
        val updatedUser = EditUser(name, location, bio)
        editUserInfoPresenter.onClickButton(updatedUser)
    }

    override fun editUser() {
        TODO("Not yet implemented")
    }

    override fun onSuccess() {
        TODO("Not yet implemented")
    }

    override fun onError(message: String) {
        TODO("Not yet implemented")
    }
}