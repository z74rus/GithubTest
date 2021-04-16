package ru.zaytsev.githubtest.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator
import ru.zaytsev.githubtest.adapters.user.UserAdapter
import ru.zaytsev.githubtest.databinding.FragmentSearchUsersBinding
import ru.zaytsev.githubtest.di.GithubApp
import ru.zaytsev.githubtest.models.User
import javax.inject.Inject


class SearchUsersFragment: Fragment() {

    private var _binding: FragmentSearchUsersBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private  lateinit var viewModel: SearchUsersViewModel

    private var userAdapter: UserAdapter? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        GithubApp.mainComponent.inject(this)
        viewModel = ViewModelProvider(this, viewModelFactory).get(SearchUsersViewModel::class.java)
        _binding = FragmentSearchUsersBinding.inflate(inflater, container, false)

        bindViewModel()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        userAdapter = UserAdapter { user ->
            onItemClicked(user)
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
                viewModel.search(query)
            }
        }
    }

    private fun bindViewModel() {
        viewModel.users.observe(viewLifecycleOwner) { users ->
            userAdapter?.items = users
        }
        viewModel.isLoading.observe(viewLifecycleOwner) {
            isLoadingState(it)
        }
    }

    private fun isLoadingState(isLoading: Boolean) {
        with(binding) {
            listReposRv.isVisible = !isLoading
            searchBtn.isEnabled = !isLoading
            searchReposEt.isEnabled = !isLoading
            progressBar.isVisible = isLoading
        }
    }

    private fun onItemClicked(user: User) {
        val action =
            SearchUsersFragmentDirections.actionSearchUsersFragmentToUserDetailFragment(user.name)
        findNavController().navigate(action)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        userAdapter = null
    }
}