package ru.zaytsev.githubtest

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch


class SearchUsersViewModel : ViewModel() {

    private val userListLiveData = MutableLiveData<List<User>>()
    private val isLoadingLiveData = MutableLiveData<Boolean>()
    private val repository = SearchUsersRepository()

    val users: LiveData<List<User>>
        get() = userListLiveData
    val isLoading: LiveData<Boolean>
        get() = isLoadingLiveData

    fun search(query: String) {
        viewModelScope.launch {
            isLoadingLiveData.postValue(true)
            try {
                val users = repository.searchUsers(query)
                userListLiveData.postValue(users)
            } catch (t: Throwable) {
                Log.e("ErrorScope", "${t.message}")
                userListLiveData.postValue(emptyList())
            } finally {
                isLoadingLiveData.postValue(false)
            }
        }
    }
}