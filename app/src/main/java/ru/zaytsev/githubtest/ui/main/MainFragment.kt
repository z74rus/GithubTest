package ru.zaytsev.githubtest.ui.main

import android.content.Context
import android.os.Bundle
import android.view.*

import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe

import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import ru.zaytsev.githubtest.R
import ru.zaytsev.githubtest.databinding.FragmentMainBinding
import ru.zaytsev.githubtest.models.DetailUser
import ru.zaytsev.githubtest.ui.auth.StartFragment
import androidx.lifecycle.ViewModelProviders
import ru.zaytsev.githubtest.utils.GithubApp
import javax.inject.Inject

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
    }
    private var currentUser: DetailUser? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.editInfoMenu -> {
                findNavController().navigate(MainFragmentDirections.actionMainFragmentToEditUserInfoFragment())
                true
            }
            R.id.logOut -> {
                logOut()
                true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.repositoriesBtn.setOnClickListener {
            findNavController().navigate(
                MainFragmentDirections.actionMainFragmentToUserDetailFragment(
                    currentUser?.userName ?: ""
                )
            )
        }
        binding.searchUsersBtn.setOnClickListener {
            findNavController().navigate(MainFragmentDirections.actionMainFragmentToSearchUsersFragment())
        }

        bindViewModel()

    }

    private fun bindViewModel() {
        viewModel.getUserInfo()
        viewModel.user.observe(viewLifecycleOwner) { user ->
            user?.let {
                currentUser = user
                with(binding) {
                    Glide.with(this@MainFragment)
                        .load(user.avatar)
                        .into(avatarIv)
                    loginTv.text = user.userName
                    nameTv.text = user.name
                    shortInfoTv.text = user.bio
                }
            }
        }

        viewModel.isLoading.observe(viewLifecycleOwner, ::isLoadingState)
    }

    private fun isLoadingState(isLoading: Boolean) {
        with(binding) {
            cardView.isVisible = !isLoading
            progressBar.isVisible = isLoading
        }
    }


    private fun logOut() {
        StartFragment.isLogOut = true
        findNavController().navigate(MainFragmentDirections.actionMainFragmentToStartFragment())
    }

}