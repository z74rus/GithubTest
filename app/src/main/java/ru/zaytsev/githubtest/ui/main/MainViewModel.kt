package ru.zaytsev.githubtest.ui.main

import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import ru.zaytsev.githubtest.data.MainRepository
import ru.zaytsev.githubtest.models.DetailUser
import ru.zaytsev.githubtest.models.User


class MainViewModel : ViewModel() {
    private val userLiveData: MutableLiveData<DetailUser> = MutableLiveData()
    private val isLoadingLiveData: MutableLiveData<Boolean> = MutableLiveData(false)
    private val userIdLiveData = MutableLiveData<Long>()
    private val repository = MainRepository.get()
    private val usersLiveData = repository.getDetailUsers()

    val user: LiveData<DetailUser>
        get() = userLiveData
    val isLoading: LiveData<Boolean>
        get() = isLoadingLiveData

    var userDetailLiveDataDb: LiveData<DetailUser?> =
        Transformations.switchMap(userIdLiveData) { userId ->
            repository.getUserInfoFromDatabase(userId)
        }

    fun getUserInfo() {
        viewModelScope.launch {
            isLoadingLiveData.postValue(true)
            try {
                val userResult = async { repository.getUserInfo() }
                val user = userResult.await()
                Log.d("ERROR", "user = $user")
                if(usersLiveData.value != null) {
                    if (usersLiveData.value!!.isNotEmpty()) {
                        updateUserInfo(user)
                        Log.d("ERROR", "merge if")
                    }
                } else {
                    Log.d("ERROR", "merge else")
                    loadUserInfo(user)
                }

                val updatedUser: DetailUser =  userDetailLiveDataDb.value ?: user ?: DetailUser("Kusa")
                userLiveData.postValue(updatedUser)
            } catch (t: Throwable) {
                Log.e("ERROR", "Error = $t")
                val defaultUser: DetailUser = userDetailLiveDataDb.value ?: DetailUser()
                userLiveData.postValue(defaultUser)
            } finally {
                isLoadingLiveData.postValue(false)
            }
        }
    }

    private fun loadUserInfo(user: DetailUser?) {
        repository.loadDetailUserInfo(user)
    }

    private fun updateUserInfo(user: DetailUser?) {
        repository.updateUserInfo(user)
    }

}