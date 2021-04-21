package com.example.githubmvp.mvp.presenters

import com.example.githubmvp.models.EditUser
import com.example.githubmvp.mvp.models.EditUserInfoRepo
import com.example.githubmvp.mvp.views.EditUserView
import com.example.githubmvp.utils.launchWithErrorHandler
import moxy.MvpPresenter
import moxy.presenterScope
import javax.inject.Inject

class EditUserInfoPresenter@Inject constructor(
    private val editUserInfoRepo: EditUserInfoRepo
): MvpPresenter<EditUserView>() {

    fun onClickButton(editUser:EditUser) {
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