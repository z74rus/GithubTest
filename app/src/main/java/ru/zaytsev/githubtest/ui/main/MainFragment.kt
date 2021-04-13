package ru.zaytsev.githubtest.ui.main

import android.os.Bundle
import android.view.*
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavigatorProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import ru.zaytsev.githubtest.R
import ru.zaytsev.githubtest.databinding.FragmentMainBinding
import ru.zaytsev.githubtest.models.DetailUser
import ru.zaytsev.githubtest.ui.auth.StartFragment

class MainFragment : Fragment(R.layout.fragment_main) {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by viewModels()
    private var currentUser: DetailUser? = null


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