package com.example.githubmvp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.githubmvp.di.GithubApp
import com.example.githubmvp.adapters.repo.RepoAdapter
import com.example.githubmvp.databinding.FragmentUserDetailBinding
import com.example.githubmvp.models.Repo
import com.example.githubmvp.mvp.presenters.UserDetailPresenter
import com.example.githubmvp.mvp.views.UserDetailView
import com.example.githubmvp.utils.withArguments
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import com.example.githubmvp.models.DetailUser
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject


class UserDetailFragment : MvpAppCompatFragment(), UserDetailView {

    private var _binding: FragmentUserDetailBinding? = null
    private val binding get() = _binding!!
    private var repoAdapter: RepoAdapter? = null

    @Inject
    @InjectPresenter
    lateinit var userDetailPresenter: UserDetailPresenter

    @ProvidePresenter
    fun provideUserDetailPresenter() = userDetailPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        GithubApp.mainComponent.inject(this)
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUserDetailBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        val userName = requireArguments().getString(KEY_DETAIL).toString()
        userDetailPresenter.getUserInfo(userName)

    }

    private fun initUI() {
        repoAdapter = RepoAdapter()
        with(binding.reposRv) {
            adapter = repoAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        repoAdapter = null
    }


    override fun showInfo(user: DetailUser) {
        with(binding) {
            Glide.with(this@UserDetailFragment)
                .load(user.avatar)
                .into(avatarIv)
            nameTv.text = user.name
            userNameTv.text = user.userName
            followersTv.text = "Followers: ${user.followers}"
            followingTv.text = "Following: ${user.following}"
        }
    }

    override fun showRepos(repos: List<Repo>) {
        repoAdapter?.items = repos
    }

    override fun showLoading(isLoading: Boolean) {
        with(binding) {
            reposRv.isVisible = !isLoading
            avatarIv.isVisible = !isLoading
            nameTv.isVisible = !isLoading
            userNameTv.isVisible = !isLoading
            layout.isVisible = !isLoading
            progressBar.isVisible = isLoading
        }
    }

    override fun onError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    companion object {

        private const val KEY_DETAIL = "KEY_DETAIL"

        fun newInstance(userName: String): UserDetailFragment {
            return UserDetailFragment().withArguments {
                putString(KEY_DETAIL, userName)
            }
        }
    }
}