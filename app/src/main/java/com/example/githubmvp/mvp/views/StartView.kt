package com.example.githubmvp.mvp.views

import android.content.Intent
import com.google.android.material.bottomsheet.BottomSheetBehavior
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.SkipStrategy
import moxy.viewstate.strategy.StateStrategyType

interface StartView: MvpView {

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun onError(message: String)

    @StateStrategyType(SkipStrategy::class)
    fun openLoginPage(intent: Intent)

    @StateStrategyType(SkipStrategy::class)
    fun onSuccess(message: String)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun onSuccessLogin()

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun isLoading(isLoading: Boolean)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun onClickButtonWithoutLogin()
    
}