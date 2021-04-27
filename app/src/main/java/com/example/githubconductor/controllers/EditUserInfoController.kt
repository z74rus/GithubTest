package com.example.githubconductor.controllers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.githubconductor.databinding.ControllerEditUserInfoBinding
import com.example.githubconductor.di.GithubApp
import com.example.githubconductor.models.EditUser
import com.example.githubconductor.mvp.presenters.EditUserInfoPresenter
import com.example.githubconductor.utils.MoxyController
import com.example.githubconductor.mvp.views.EditUserView
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject

class EditUserInfoController: MoxyController(), EditUserView {

    private var _binding: ControllerEditUserInfoBinding? = null
    private val binding get() = _binding!!

    @Inject
    @InjectPresenter
    lateinit var editUserInfoPresenter: EditUserInfoPresenter

    @ProvidePresenter
    fun provideEditPresenter() = editUserInfoPresenter


    private fun initUI() {
        binding.acceptButton.setOnClickListener {
            updateInfo()
        }
    }

    private fun updateInfo() {
        val name = binding.nameEditText.text.toString()
        val location = binding.locationEditText.text.toString()
        val bio = binding.bioEditText.text.toString()
        val updatedUser = EditUser(name, location, bio)
        editUserInfoPresenter.onClickButton(updatedUser)
    }

    override fun onSuccess() {
        Toast.makeText(activity, "Success", Toast.LENGTH_SHORT).show()
    }

    override fun onError(message: String) {
        Toast.makeText(activity, "Error = $message", Toast.LENGTH_SHORT).show()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup,
        savedViewState: Bundle?
    ): View {
        _binding = ControllerEditUserInfoBinding.inflate(inflater, container, false)
        GithubApp.mainComponent.inject(this@EditUserInfoController)
        initUI()
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}