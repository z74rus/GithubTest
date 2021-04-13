package ru.zaytsev.githubtest.ui.main

import androidx.lifecycle.*
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import ru.zaytsev.githubtest.data.MainRepository
import ru.zaytsev.githubtest.models.DetailUser
import ru.zaytsev.githubtest.models.User


class MainViewModel: ViewModel() {
    private val userLiveData: MutableLiveData<DetailUser> = MutableLiveData()
    private val isLoadingLiveData: MutableLiveData<Boolean> = MutableLiveData(false)
    private val userIdLiveData = MutableLiveData<Long>()
    private val repository = MainRepository.get()

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
                userLiveData.postValue(user)
                if(userDetailLiveDataDb.value != null) {
                   updateUserInfo(user)
                } else {
                    loadUserInfo(user)
                }
            } catch (t:Throwable) {
                val defaultUser: DetailUser = userDetailLiveDataDb.value ?: DetailUser()
                userLiveData.postValue(defaultUser)
            } finally {
                isLoadingLiveData.postValue(false)
            }
        }
    }

    private fun loadUserInfo(user: DetailUser) {
        repository.loadDetailUserInfo(user)
    }

    private fun updateUserInfo(user: DetailUser) {
        repository.updateUserInfo(user)
    }

}