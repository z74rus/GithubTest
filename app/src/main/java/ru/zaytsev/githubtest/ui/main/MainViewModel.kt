package ru.zaytsev.githubtest.ui.main

import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.*
import ru.zaytsev.githubtest.data.MainRepository
import ru.zaytsev.githubtest.models.DetailUser
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val repository: MainRepository
): ViewModel() {
    private val userLiveData: MutableLiveData<DetailUser?> = MutableLiveData()
    private val isLoadingLiveData: MutableLiveData<Boolean> = MutableLiveData(false)

    val user: LiveData<DetailUser?>
        get() = userLiveData
    val isLoading: LiveData<Boolean>
        get() = isLoadingLiveData

    init {
        Log.d("InitVIEWMODEL","init $this")
    }



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