package com.example.githubmvp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubmvp.R
import com.example.githubmvp.di.GithubApp
import com.example.githubmvp.databinding.FragmentSearchUsersBinding
import com.example.githubmvp.adapters.user.UserAdapter
import com.example.githubmvp.models.User
import com.example.githubmvp.mvp.presenters.SearchUserPresenter
import com.example.githubmvp.mvp.views.SearchUsersView
import com.example.githubmvp.utils.withArguments
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject


class SearchUsersFragment : MvpAppCompatFragment(), SearchUsersView {

    private var _binding: FragmentSearchUsersBinding? = null
    private val binding get() = _binding!!
    private var userAdapter: UserAdapter? = null

    @Inject
    @InjectPresenter
    lateinit var searchUserPresenter: SearchUserPresenter

    @ProvidePresenter
    fun provideSearchPresenter() = searchUserPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        GithubApp.mainComponent.inject(this)
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchUsersBinding.inflate(inflater, container, false)
        initUI()
        return binding.root
    }


    private fun initUI() {
        userAdapter = UserAdapter { user ->
            searchUserPresenter.onClickUser(user)
        }

        with(binding.listReposRv) {
            adapter = userAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            itemAnimator = SlideInLeftAnimator()
        }

        with(binding) {
            searchBtn.setOnClickListener {
                val query = searchReposEt.text.toString()
                searchUserPresenter.onSearchButtonClick(query = query)
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        userAdapter = null
    }

    override fun onClickSearch(users: List<User>) {
        userAdapter?.submitList(users)
    }

    override fun showLoading(isShow: Boolean) {
        with(binding) {
            progressBar.isVisible = isShow
            searchBtn.isEnabled = !isShow
        }
    }


    override fun onError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onClickUser(user: User) {
        requireFragmentManager().beginTransaction()
            .replace(R.id.parentContainer, UserDetailFragment().withArguments {
                putString(KEY_DETAIL, user.name)
            })
            .addToBackStack("UserDetailFragment")
            .commit()
    }


    companion object {
        private const val KEY_DETAIL = "KEY_DETAIL"
    }

}