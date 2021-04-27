package com.example.githubconductor.mvp.presenters

import com.example.githubconductor.mvp.models.MainRepository
import com.example.githubconductor.mvp.views.MainView
import moxy.MvpPresenter
import moxy.presenterScope
import com.example.githubconductor.models.DetailUser
import com.example.githubconductor.utils.launchWithErrorHandler
import moxy.InjectViewState
import javax.inject.Inject

@InjectViewState
class MainPresenter @Inject constructor(
    private val mainRepository: MainRepository
) : MvpPresenter<MainView>() {


    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.showLoading(isShow = true)
        presenterScope.launchWithErrorHandler(block = {
            val user = mainRepository.getUserInfo() ?: DetailUser(userName = "@VasyaPupkin")
            viewState.showShortInfo(user)
            viewState.showLoading(false)
        }, onError = {
            viewState.showLoading(false)
        })
    }

    fun onRepositoriesClick(detailUserName: String) {
        viewState.openRepositoriesScreen(detailUserName)
    }

    fun onSearchClick() {
        viewState.openSearchUsersScreen()
    }

    fun onLogOutClick() {
        mainRepository.logOut()
        viewState.onClickLogOut()
    }

    fun onEditUserClick() {
        viewState.openEditUserScreen()
    }
}