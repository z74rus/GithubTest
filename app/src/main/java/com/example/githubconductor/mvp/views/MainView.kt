package com.example.githubconductor.mvp.views

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType
import com.example.githubconductor.models.DetailUser
import moxy.viewstate.strategy.AddToEndStrategy

interface MainView: MvpView {

    @StateStrategyType(AddToEndStrategy::class)
    fun showShortInfo(detailUser: DetailUser)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun openRepositoriesScreen(detailUserName: String)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun openSearchUsersScreen()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showLoading(isShow: Boolean)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun openEditUserScreen()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun onClickLogOut()
}