package com.example.githubmvp.mvp.presenters

import com.example.githubmvp.mvp.models.UserDetailRepo
import com.example.githubmvp.mvp.views.UserDetailView
import com.example.githubmvp.utils.launchWithErrorHandler
import moxy.MvpPresenter
import moxy.presenterScope
import com.example.githubmvp.models.DetailUser
import javax.inject.Inject

class UserDetailPresenter @Inject constructor(
    private val userDetailRepo: UserDetailRepo
) : MvpPresenter<UserDetailView>() {

    fun getUserInfo(userName: String) {
        presenterScope.launchWithErrorHandler(
            block = {
                viewState.showLoading(true)
                val user = userDetailRepo.getUserDetail(userName = userName)?: DetailUser("@VASYOK")
                val repos = userDetailRepo.getRepos(userName = userName)
                viewState.showInfo(user)
                viewState.showRepos(repos)
                viewState.showLoading(false)
            }, onError = {
                viewState.showLoading(false)
                viewState.showRepos(emptyList())
                viewState.showInfo(DetailUser("@VASYOK"))
                viewState.onError("Error = $it")
            }
        )
    }
}