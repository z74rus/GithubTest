package com.example.githubconductor.controllers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubconductor.R
import com.example.githubconductor.adapters.repo.RepoAdapter
import com.example.githubconductor.di.GithubApp
import com.example.githubconductor.models.DetailUser
import com.example.githubconductor.models.Repo
import com.example.githubconductor.mvp.presenters.UserDetailPresenter
import com.example.githubconductor.utils.MoxyController
import com.example.githubconductor.mvp.views.UserDetailView
import de.hdodenhof.circleimageview.CircleImageView
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject


class UserDetailController : MoxyController, UserDetailView {

    constructor() : super()
    constructor(args: Bundle?) : super(args)

    private var repoAdapter: RepoAdapter? = null
    private lateinit var reposRv: RecyclerView
    private lateinit var avatarIv: CircleImageView
    private lateinit var nameTv: TextView
    private lateinit var userNameTv: TextView
    private lateinit var followersTv: TextView
    private lateinit var followingTv: TextView
    private lateinit var progressBar: ProgressBar

    @Inject
    @InjectPresenter
    lateinit var userDetailPresenter: UserDetailPresenter

    @ProvidePresenter
    fun provideUserDetailPresenter() = userDetailPresenter


    private fun initUI() {
        repoAdapter = RepoAdapter()
        with(reposRv) {
            adapter = repoAdapter
            layoutManager = LinearLayoutManager(activity)
            setHasFixedSize(true)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        repoAdapter = null
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup,
        savedViewState: Bundle?
    ): View = inflater.inflate(R.layout.controller_user_detail, container, false).apply {
        GithubApp.mainComponent.inject(this@UserDetailController)
        val userName = args.getString(KEY_DETAIL).toString()
        userDetailPresenter.getUserInfo(userName)
        reposRv = findViewById(R.id.reposRv)
        avatarIv = findViewById(R.id.avatarIv)
        nameTv = findViewById(R.id.nameTv)
        userNameTv = findViewById(R.id.userNameTv)
        followersTv = findViewById(R.id.followersTv)
        followingTv = findViewById(R.id.followingTv)
        progressBar = findViewById(R.id.progressBar)
        initUI()
    }


    override fun showInfo(user: DetailUser) {
        Glide.with(activity!!)
            .load(user.avatar)
            .into(avatarIv)
        nameTv.text = user.name
        userNameTv.text = user.userName
        followersTv.text = "Followers: ${user.followers}"
        followingTv.text = "Following: ${user.following}"

    }

    override fun showRepos(repos: List<Repo>) {
        repoAdapter?.items = repos
    }

    override fun showLoading(isLoading: Boolean) {
        reposRv.isVisible = !isLoading
        avatarIv.isVisible = !isLoading
        nameTv.isVisible = !isLoading
        userNameTv.isVisible = !isLoading
        followingTv.isVisible = !isLoading
        followersTv.isVisible = !isLoading
        progressBar.isVisible = isLoading
    }

    override fun onError(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }

    companion object {

        private const val KEY_DETAIL = "KEY_DETAIL"

        fun newInstance(userName: String): UserDetailController {
            return UserDetailController(Bundle().apply {
                putString(KEY_DETAIL, userName)
            })
        }
    }
}