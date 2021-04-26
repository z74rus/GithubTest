package com.example.githubconductor.utils

import android.os.Bundle
import android.view.View
import com.bluelinelabs.conductor.Controller
import moxy.MvpDelegate
import moxy.MvpDelegateHolder

abstract class MoxyController: Controller, MvpDelegateHolder {

    constructor():super()
    constructor(args: Bundle?): super(args)

    private val delegate = MvpDelegate(this)

    override fun getMvpDelegate(): MvpDelegate<*> = delegate

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mvpDelegate.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        mvpDelegate.onCreate(savedInstanceState)
    }

    override fun onAttach(view: View) {
        super.onAttach(view)
        mvpDelegate.onCreate()
        mvpDelegate.onAttach()
    }

    override fun onDetach(view: View) {
        super.onDetach(view)
        mvpDelegate.onDetach()
    }

    override fun onDestroyView(view: View) {
        super.onDestroyView(view)
        mvpDelegate.onDestroyView()
    }

    override fun onDestroy() {
        super.onDestroy()
        mvpDelegate.onDestroy()
    }
}