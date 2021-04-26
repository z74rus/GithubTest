package com.example.githubconductor.controllers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.example.githubconductor.R
import com.example.githubconductor.di.GithubApp
import com.example.githubconductor.models.EditUser
import com.example.githubconductor.mvp.presenters.EditUserInfoPresenter
import com.example.githubconductor.utils.MoxyController
import com.example.githubconductor.mvp.views.EditUserView
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject

class EditUserInfoController: MoxyController(), EditUserView {

    private lateinit var acceptButton: Button
    private lateinit var nameEditText: EditText
    private lateinit var locationEditText: EditText
    private lateinit var bioEditText: EditText

    @Inject
    @InjectPresenter
    lateinit var editUserInfoPresenter: EditUserInfoPresenter

    @ProvidePresenter
    fun provideEditPresenter() = editUserInfoPresenter


    private fun initUI() {
        acceptButton.setOnClickListener {
            updateInfo()
        }
    }

    private fun updateInfo() {
        val name = nameEditText.text.toString()
        val location = locationEditText.text.toString()
        val bio = bioEditText.text.toString()
        val updatedUser = EditUser(name, location, bio)
        editUserInfoPresenter.onClickButton(updatedUser)
    }

    override fun onSuccess() {
        TODO("Not yet implemented")
    }

    override fun onError(message: String) {
        TODO("Not yet implemented")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup,
        savedViewState: Bundle?
    ): View = inflater.inflate(R.layout.controller_edit_user_info, container, false).apply {
        GithubApp.mainComponent.inject(this@EditUserInfoController)
        acceptButton = findViewById(R.id.acceptButton)
        nameEditText = findViewById(R.id.nameEditText)
        locationEditText = findViewById(R.id.locationEditText)
        bioEditText = findViewById(R.id.bioEditText)
        initUI()
    }
}