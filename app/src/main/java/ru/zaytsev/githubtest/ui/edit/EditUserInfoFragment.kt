package ru.zaytsev.githubtest.ui.edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.zaytsev.githubtest.databinding.FragmentEditUserInfoBinding
import ru.zaytsev.githubtest.models.EditUser

class EditUserInfoFragment: Fragment() {
    private val viewModel: EditUserInfoViewModel by viewModels()
    private var _binding:FragmentEditUserInfoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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