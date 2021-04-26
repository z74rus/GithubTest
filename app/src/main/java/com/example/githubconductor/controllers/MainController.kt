package com.example.githubconductor.controllers

import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import com.bluelinelabs.conductor.RouterTransaction
import com.bumptech.glide.Glide
import com.example.githubconductor.R
import com.example.githubconductor.models.DetailUser
import com.example.githubconductor.mvp.presenters.MainPresenter
import com.example.githubconductor.di.GithubApp
import com.example.githubconductor.utils.MoxyController
import com.example.githubconductor.mvp.views.MainView
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject


class MainController : MoxyController(), MainView {

    private var currentUser: DetailUser? = null
    private lateinit var searchUsersBtn: Button
    private lateinit var repositoriesBtn: Button
    private lateinit var avatarIv: ImageView
    private lateinit var loginTv: TextView
    private lateinit var nameTv: TextView
    private lateinit var shortInfoTv: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var cardView: CardView

    @Inject
    @InjectPresenter
    lateinit var presenter: MainPresenter

    @ProvidePresenter
    fun provideMainPresenter() = presenter

    private fun initButtons() {
        repositoriesBtn.setOnClickListener {
            presenter.onRepositoriesClick(
                currentUser?.userName ?: "test"
            )
        }
        searchUsersBtn.setOnClickListener {
            presenter.onSearchClick()
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup,
        savedViewState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.controller_main, container, false)
        GithubApp.mainComponent.inject(this)
        searchUsersBtn = view.findViewById(R.id.searchUsersBtn)
        repositoriesBtn = view.findViewById(R.id.repositoriesBtn)
        avatarIv = view.findViewById(R.id.avatarIv)
        loginTv = view.findViewById(R.id.loginTv)
        nameTv = view.findViewById(R.id.nameTv)
        shortInfoTv = view.findViewById(R.id.shortInfoTv)
        progressBar = view.findViewById(R.id.progressBar)
        cardView = view.findViewById(R.id.cardView)
        setHasOptionsMenu(true)
        initButtons()
        return view
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.logOut -> {
                presenter.onLogOutClick()
                true
            }
            R.id.editInfoMenu -> {
                presenter.onEditUserClick()
                true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun showShortInfo(detailUser: DetailUser) {
        currentUser = detailUser
        Glide.with(activity!!)
            .load(detailUser.avatar)
            .into(avatarIv)
        loginTv.text = detailUser.userName
        nameTv.text = detailUser.name
        shortInfoTv.text = detailUser.bio
    }

    override fun openRepositoriesScreen(detailUserName: String) {
        router.pushController(RouterTransaction.with(UserDetailController.newInstance(detailUserName)))
    }


    override fun openSearchUsersScreen() {
        router.pushController(RouterTransaction.with(SearchUsersController()))
    }

    override fun showLoading(isShow: Boolean) {
        progressBar.isVisible = isShow
        cardView.isVisible = !isShow
    }

    override fun openEditUserScreen() {
        router.pushController(RouterTransaction.with(EditUserInfoController()))
    }

    override fun handleBack(): Boolean {
        router.popCurrentController()
        return super.handleBack()
    }

    override fun onClickLogOut() {
        StartController.isLogOut = true
    }
}