package ru.zaytsev.githubtest

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import ru.zaytsev.githubtest.adapters.repo.RepoAdapter
import ru.zaytsev.githubtest.databinding.FragmentUserDetailBinding

class UserDetailFragment: Fragment() {
    private var _binding: FragmentUserDetailBinding? = null
    private val binding get() = _binding!!

    private val args: UserDetailFragmentArgs by navArgs()
    private val viewModel: UserDetailViewModel by viewModels()
    private var repoAdapter: RepoAdapter? = null


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
        bindViewModel()
        initUI()
    }

    private fun bindViewModel() {
        viewModel.isLoading.observe(viewLifecycleOwner) {
            isLoadingState(it)
        }
        viewModel.repositories.observe(viewLifecycleOwner) { repos ->
            Log.d("ERRORVIEWMODEL", "fragment before adapter $repos")
            repoAdapter?.items = repos
            Log.d("ERRORVIEWMODEL", " in observer ${repoAdapter?.items}")
        }
        viewModel.user.observe(viewLifecycleOwner) { detailUser ->
            with(binding) {
                Glide.with(this@UserDetailFragment)
                    .load(args.user.avatar)
                    .into(avatarIv)
                followersTv.text = "Followes: ${detailUser.followers}"
                followingTv.text = "Following: ${detailUser.following}"
                nameTv.text = detailUser.name
                userNameTv.text = detailUser.userName
            }
        }
        viewModel.getUserDetail(args.user.name)
    }

    private fun isLoadingState(isLoading: Boolean) {
        Log.d("ERRORVIEWMODEL", "isLoading $isLoading")
        with(binding) {
            reposRv.isVisible = !isLoading
            Log.d("ERRORVIEWMODEL", "visibility rv = ${reposRv.isVisible}")
            avatarIv.isVisible = !isLoading
            nameTv.isVisible = !isLoading
            userNameTv.isVisible = !isLoading
            layout.isVisible = !isLoading
            progressBar.isVisible = isLoading
        }
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
}