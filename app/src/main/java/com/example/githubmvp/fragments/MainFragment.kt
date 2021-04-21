package com.example.githubmvp.fragments

import android.os.Bundle
import android.view.*

import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.example.githubmvp.di.GithubApp
import com.example.githubmvp.R
import com.example.githubmvp.data.database.UserDao
import com.example.githubmvp.data.network.GithubApi
import com.example.githubmvp.databinding.FragmentMainBinding
import com.example.githubmvp.mvp.presenters.MainPresenter
import com.example.githubmvp.mvp.views.MainView

import moxy.MvpAppCompatFragment
import com.example.githubmvp.models.DetailUser
import com.example.githubmvp.mvp.models.MainRepository
import com.example.githubmvp.utils.withArguments
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject


class MainFragment : MvpAppCompatFragment(), MainView {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private var currentUser: DetailUser? = null

    @Inject
    @InjectPresenter
    lateinit var presenter: MainPresenter

    @ProvidePresenter
    fun provideMainPresenter() = presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        GithubApp.mainComponent.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        initButtons()
        return binding.root
    }

    private fun initButtons() {
        with(binding) {
            repositoriesBtn.setOnClickListener {
                presenter.onRepositoriesClick(
                    currentUser?.userName ?: "test"
                )
            }
            searchUsersBtn.setOnClickListener {
                presenter.onSearchClick()
            }
        }
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
        with(binding) {
            Glide.with(this@MainFragment)
                .load(detailUser.avatar)
                .into(avatarIv)
            loginTv.text = detailUser.userName
            nameTv.text = detailUser.name
            shortInfoTv.text = detailUser.bio
        }
    }

    override fun openRepositoriesScreen(detailUserName: String) {
        requireFragmentManager().beginTransaction()
            .replace(R.id.parentContainer, UserDetailFragment().withArguments {
                putString(KEY_DETAIL, detailUserName)
            })
            .addToBackStack("UserDetailFragment")
            .commit()
    }


    override fun openSearchUsersScreen() {
        requireFragmentManager().beginTransaction()
            .replace(R.id.parentContainer, SearchUsersFragment())
            .addToBackStack("SearchUsersFragment")
            .commit()
    }

    override fun showLoading(isShow: Boolean) {
        with(binding) {
            progressBar.isVisible = isShow
            cardView.isVisible = !isShow
        }
    }

    override fun openEditUserScreen() {
        requireFragmentManager().beginTransaction()
            .replace(R.id.parentContainer, EditUserInfoFragment())
            .addToBackStack("EditUserInfoFragment")
            .commit()
    }

    override fun onClickLogOut() {
        StartFragment.isLogOut = true
        requireFragmentManager().beginTransaction()
            .replace(R.id.parentContainer, StartFragment())
            .commit()
    }

    companion object {
        private const val KEY_DETAIL = "KEY_DETAIL"
    }

}