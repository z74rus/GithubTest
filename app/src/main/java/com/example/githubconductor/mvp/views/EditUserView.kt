package com.example.githubconductor.mvp.views

import moxy.MvpView
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

interface EditUserView : MvpView {

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun onSuccess()

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun onError(message: String)
}