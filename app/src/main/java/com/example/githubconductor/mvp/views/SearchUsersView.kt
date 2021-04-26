package com.example.githubconductor.mvp.views

import com.example.githubconductor.models.User
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.SkipStrategy
import moxy.viewstate.strategy.StateStrategyType


interface SearchUsersView : MvpView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun onClickSearch(users: List<User>)

    @StateStrategyType(SkipStrategy::class)
    fun showLoading(isShow: Boolean)

    @StateStrategyType(SkipStrategy::class)
    fun onError(message: String)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun onClickUser(user: User)

}