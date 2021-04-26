package com.example.githubconductor.mvp.presenters

import android.util.Log
import com.example.githubconductor.mvp.models.UserDetailRepo
import com.example.githubconductor.mvp.views.UserDetailView
import moxy.MvpPresenter
import moxy.presenterScope
import com.example.githubconductor.models.DetailUser
import com.example.githubconductor.utils.launchWithErrorHandler
import moxy.InjectViewState
import javax.inject.Inject
@InjectViewState
class UserDetailPresenter @Inject constructor(
    private val userDetailRepo: UserDetailRepo
) : MvpPresenter<UserDetailView>() {

    fun getUserInfo(userName: String) {
        Log.d("ERRORPUK", "username form userDetailPresenter = $userName")
        presenterScope.launchWithErrorHandler(
            block = {
                viewState.showLoading(true)
                val repos = userDetailRepo.getRepos(userName = userName)
                val user = userDetailRepo.getUserDetail(userName = userName)?: DetailUser("@VASYOK")
                viewState.showInfo(user)
                viewState.showRepos(repos)
                viewState.showLoading(false)
            }, onError = {
                Log.d("ERRORPUK", "ERROR in USer Detail presenter = $it")
                viewState.showLoading(false)
                viewState.showRepos(emptyList())
                viewState.showInfo(DetailUser("@VASYOK"))
                viewState.onError("Error = $it")
            }
        )
    }
}