package com.example.githubconductor.mvp.presenters

import com.example.githubconductor.models.EditUser
import com.example.githubconductor.mvp.models.EditUserInfoRepo
import com.example.githubconductor.utils.launchWithErrorHandler
import com.example.githubconductor.mvp.views.EditUserView
import moxy.InjectViewState
import moxy.MvpPresenter
import moxy.presenterScope
import javax.inject.Inject

@InjectViewState
class EditUserInfoPresenter@Inject constructor(
    private val editUserInfoRepo: EditUserInfoRepo
): MvpPresenter<EditUserView>() {

    fun onClickButton(editUser: EditUser) {
        presenterScope.launchWithErrorHandler(
            block = {
                editUserInfoRepo.editUser(editUser)
                viewState.onSuccess()
            }, onError = {
                viewState.onError("Error = $it")
            }
        )
    }
}