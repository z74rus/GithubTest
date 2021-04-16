package ru.zaytsev.githubtest.ui.edit

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.zaytsev.githubtest.data.EditUserInfoRepo
import ru.zaytsev.githubtest.models.EditUser
import javax.inject.Inject

class EditUserInfoViewModel @Inject constructor(private val repository: EditUserInfoRepo): ViewModel() {

    suspend fun editInfo(
        editUser: EditUser
    ){
        viewModelScope.launch {
            try {
                repository.editUser(editUser)
            } catch (t: Throwable) {
                Log.e("ERROR","ERROR EditUserInfoViewModel at editUser: ${t.message}")
            }
        }

    }
}