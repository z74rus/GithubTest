package ru.zaytsev.githubtest.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import ru.zaytsev.githubtest.data.MainRepository
import ru.zaytsev.githubtest.models.DetailUser


class MainViewModel: ViewModel() {
    private val userLiveData: MutableLiveData<DetailUser> = MutableLiveData()
    private val isLoadingLiveData: MutableLiveData<Boolean> = MutableLiveData(false)

    private val repository = MainRepository()

    val user: LiveData<DetailUser>
        get() = userLiveData
    val isLoading: LiveData<Boolean>
        get() = isLoadingLiveData

    fun getUserInfo() {
        viewModelScope.launch {
            isLoadingLiveData.postValue(true)
            try {
                val userResult = async { repository.getUserInfo() }
                val user = userResult.await()
                userLiveData.postValue(user)
            } catch (t:Throwable) {
                userLiveData.postValue(DetailUser())
            } finally {
                isLoadingLiveData.postValue(false)
            }
        }
    }
}