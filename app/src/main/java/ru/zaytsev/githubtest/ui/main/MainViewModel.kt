package ru.zaytsev.githubtest.ui.main

import androidx.lifecycle.*
import kotlinx.coroutines.*
import ru.zaytsev.githubtest.data.MainRepository
import ru.zaytsev.githubtest.models.DetailUser



class MainViewModel : ViewModel() {
    private val userLiveData: MutableLiveData<DetailUser?> = MutableLiveData()
    private val isLoadingLiveData: MutableLiveData<Boolean> = MutableLiveData(false)
    private val repository = MainRepository()

    val user: LiveData<DetailUser?>
        get() = userLiveData
    val isLoading: LiveData<Boolean>
        get() = isLoadingLiveData



    fun getUserInfo() {
        viewModelScope.launch {
            isLoadingLiveData.postValue(true)
            try {
                val user = repository.getUserInfo()
                userLiveData.postValue(user)
            } catch (t: Throwable) {
                t.printStackTrace()
                userLiveData.postValue(DetailUser("KUSA"))
            } finally {
                isLoadingLiveData.postValue(false)
            }
        }
    }

}