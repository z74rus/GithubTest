package com.example.githubconductor.controllers

import android.os.Bundle
import android.view.*
import androidx.core.view.isVisible
import com.bluelinelabs.conductor.RouterTransaction
import com.bumptech.glide.Glide
import com.example.githubconductor.R
import com.example.githubconductor.databinding.ControllerMainBinding
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
    private var _binding: ControllerMainBinding? = null
    private val binding get() = _binding!!

    @Inject
    @InjectPresenter
    lateinit var presenter: MainPresenter

    @ProvidePresenter
    fun provideMainPresenter() = presenter

    private fun initButtons() {
        binding.repositoriesBtn.setOnClickListener {
            presenter.onRepositoriesClick(
                currentUser?.userName ?: "test"
            )
        }
        binding.searchUsersBtn.setOnClickListener {
            presenter.onSearchClick()
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup,
        savedViewState: Bundle?
    ): View {
        _binding = ControllerMainBinding.inflate(inflater, container, false)
        GithubApp.mainComponent.inject(this)
        setHasOptionsMenu(true)
        initButtons()
        return binding.root
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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun showShortInfo(detailUser: DetailUser) {
        currentUser = detailUser
        Glide.with(activity!!)
            .load(detailUser.avatar)
            .into(binding.avatarIv)
        binding.loginTv.text = detailUser.userName
        binding.nameTv.text = detailUser.name
        binding.shortInfoTv.text = detailUser.bio
    }

    override fun openRepositoriesScreen(detailUserName: String) {
        router.pushController(RouterTransaction.with(UserDetailController.newInstance(detailUserName)))
    }


    override fun openSearchUsersScreen() {
        router.pushController(RouterTransaction.with(SearchUsersController()))
    }

    override fun showLoading(isShow: Boolean) {
        binding.progressBar.isVisible = isShow
        binding.cardView.isVisible = !isShow
    }

    override fun openEditUserScreen() {
        router.pushController(RouterTransaction.with(EditUserInfoController()))
    }

    override fun handleBack(): Boolean {
        router.popCurrentController()
        return super.handleBack()
    }

    override fun onClickLogOut() {
        router.pushController(RouterTransaction.with(StartController()))
    }
}