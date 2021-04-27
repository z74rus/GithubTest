package com.example.githubconductor.controllers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.bluelinelabs.conductor.RouterTransaction
import com.example.githubconductor.di.GithubApp
import com.example.githubconductor.models.User
import com.example.githubconductor.mvp.presenters.SearchUserPresenter
import com.example.githubconductor.adapters.user.UserAdapter
import com.example.githubconductor.databinding.ControllerSearchUsersBinding
import com.example.githubconductor.utils.MoxyController
import com.example.githubconductor.mvp.views.SearchUsersView
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject


class SearchUsersController : MoxyController(), SearchUsersView {

    private var userAdapter: UserAdapter? = null
    private var _binding: ControllerSearchUsersBinding? = null
    private val binding get() = _binding!!

    @Inject
    @InjectPresenter
    lateinit var searchUserPresenter: SearchUserPresenter

    @ProvidePresenter
    fun provideSearchPresenter() = searchUserPresenter

    private fun initUI() {
        userAdapter = UserAdapter { user ->
            searchUserPresenter.onClickUser(user)
        }

        with(binding.listReposRv) {
            adapter = userAdapter
            layoutManager = LinearLayoutManager(activity)
            setHasFixedSize(true)
            itemAnimator = SlideInLeftAnimator()
        }


        binding.searchBtn.setOnClickListener {
            val query = binding.searchReposEt.text.toString()
            searchUserPresenter.onSearchButtonClick(query = query)
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        userAdapter = null
        _binding = null
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup,
        savedViewState: Bundle?
    ): View {
        _binding = ControllerSearchUsersBinding.inflate(inflater, container, false)
            GithubApp.mainComponent.inject(this@SearchUsersController)
            initUI()
        return binding.root
    }

    override fun onClickSearch(users: List<User>) {
        userAdapter?.submitList(users)
    }

    override fun showLoading(isShow: Boolean) {
        binding.progressBar.isVisible = isShow
        binding.searchBtn.isEnabled = !isShow
    }


    override fun onError(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }

    override fun onClickUser(user: User) {
        router.pushController(RouterTransaction.with(UserDetailController.newInstance(user.name)))
    }


    companion object {
        private const val KEY_DETAIL = "KEY_DETAIL"
    }

}