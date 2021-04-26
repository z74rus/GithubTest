package com.example.githubconductor.mvp.views

import com.example.githubconductor.models.Repo
import moxy.MvpView
import com.example.githubconductor.models.DetailUser
import moxy.viewstate.strategy.*

interface UserDetailView: MvpView {

    @StateStrategyType(AddToEndStrategy::class)
    fun showInfo(user: DetailUser)

    @StateStrategyType(AddToEndStrategy::class)
    fun showRepos(repos: List<Repo>)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showLoading(isLoading: Boolean)

    @StateStrategyType(SkipStrategy::class)
    fun onError(message: String)

}