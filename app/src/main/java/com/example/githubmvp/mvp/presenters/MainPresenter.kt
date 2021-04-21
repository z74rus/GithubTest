package com.example.githubmvp.mvp.presenters

import com.example.githubmvp.mvp.models.MainRepository
import com.example.githubmvp.mvp.views.MainView
import com.example.githubmvp.utils.launchWithErrorHandler
import moxy.MvpPresenter
import moxy.presenterScope
import com.example.githubmvp.models.DetailUser
import moxy.InjectViewState
import moxy.presenter.ProvidePresenter
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
        viewState.onClickLogOut()
    }

    fun onEditUserClick() {
        viewState.openEditUserScreen()
    }
}