package ru.zaytsev.githubtest.ui.edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.zaytsev.githubtest.databinding.FragmentEditUserInfoBinding
import ru.zaytsev.githubtest.di.GithubApp
import ru.zaytsev.githubtest.models.EditUser
import javax.inject.Inject

class EditUserInfoFragment: Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: EditUserInfoViewModel
    private var _binding:FragmentEditUserInfoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        GithubApp.mainComponent.inject(this)
        viewModel = ViewModelProvider(this, viewModelFactory).get(EditUserInfoViewModel::class.java)
        _binding = FragmentEditUserInfoBinding.inflate(inflater, container, false)

        binding.acceptButton.setOnClickListener {
            updateInfo()
        }

        return binding.root
    }

    private fun updateInfo() {
        val name = binding.nameEditText.text.toString()
        val location = binding.locationEditText.text.toString()
        val bio = binding.bioEditText.text.toString()
        val updatedUser = EditUser(name, location, bio)
        val scope = CoroutineScope(Dispatchers.IO)
        scope.launch {
            viewModel.editInfo(updatedUser)
        }
    }
}