package com.example.githubconductor.controllers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bluelinelabs.conductor.RouterTransaction
import com.example.githubconductor.R
import com.example.githubconductor.di.GithubApp
import com.example.githubconductor.models.User
import com.example.githubconductor.mvp.presenters.SearchUserPresenter
import com.example.githubconductor.adapters.user.UserAdapter
import com.example.githubconductor.utils.MoxyController
import com.example.githubconductor.mvp.views.SearchUsersView
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject


class SearchUsersController : MoxyController(), SearchUsersView {

    private var userAdapter: UserAdapter? = null
    private lateinit var listReposRv: RecyclerView
    private lateinit var searchBtn: Button
    private lateinit var searchReposEt: EditText
    private lateinit var progressBar: ProgressBar

    @Inject
    @InjectPresenter
    lateinit var searchUserPresenter: SearchUserPresenter

    @ProvidePresenter
    fun provideSearchPresenter() = searchUserPresenter

    private fun initUI() {
        userAdapter = UserAdapter { user ->
            searchUserPresenter.onClickUser(user)
        }

        with(listReposRv) {
            adapter = userAdapter
            layoutManager = LinearLayoutManager(activity)
            setHasFixedSize(true)
            itemAnimator = SlideInLeftAnimator()
        }


        searchBtn.setOnClickListener {
            val query = searchReposEt.text.toString()
            searchUserPresenter.onSearchButtonClick(query = query)
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        userAdapter = null
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup,
        savedViewState: Bundle?
    ): View = inflater.inflate(R.layout.controller_search_users, container, false).apply {
        GithubApp.mainComponent.inject(this@SearchUsersController)
        listReposRv = findViewById(R.id.listReposRv)
        searchBtn = findViewById(R.id.searchBtn)
        searchReposEt = findViewById(R.id.searchReposEt)
        progressBar = findViewById(R.id.progressBar)
        initUI()
    }

    override fun onClickSearch(users: List<User>) {
        userAdapter?.submitList(users)
    }

    override fun showLoading(isShow: Boolean) {
        progressBar.isVisible = isShow
        searchBtn.isEnabled = !isShow
    }


    override fun onError(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }

    override fun onClickUser(user: User) {
        router.pushController(RouterTransaction.with(UserDetailController.newInstance(user.name)))
//        requireFragmentManager().beginTransaction()
//            .replace(R.id.parentContainer, UserDetailFragment().withArguments {
//                putString(KEY_DETAIL, user.name)
//            })
//            .addToBackStack("UserDetailFragment")
//            .commit()
    }


    companion object {
        private const val KEY_DETAIL = "KEY_DETAIL"
    }

}