package ru.zaytsev.githubtest

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class UserDetailViewModel : ViewModel() {

    private val isLoadingLiveData = MutableLiveData<Boolean>()
    private val reposLiveData = MutableLiveData<List<Repo>>()
    private val userLiveData = MutableLiveData<DetailUser>()
    private val repo = UserDetailRepo()

    val user: LiveData<DetailUser>
        get() = userLiveData

    val repositories: LiveData<List<Repo>>
        get() = reposLiveData


    val isLoading: LiveData<Boolean>
        get() = isLoadingLiveData

    fun getUserDetail(username: String) {
        viewModelScope.launch {
            Log.d("ERROR","inside viewmodelscope")
            isLoadingLiveData.postValue(true)
            try {
                val reposResult = async {
                    repo.getRepos(username)
                }

                val userResult = async {
                    repo.getUserDetail(username)
                }

                val userDetail = userResult.await()
                val repos = reposResult.await()
                Log.d("ERRORVIEWMODEL", "$repos")
                reposLiveData.postValue(repos)
                userLiveData.postValue(userDetail)

            } catch (t: Throwable) {
                Log.e("ERROR", "Error = ${t.message}")
                reposLiveData.postValue(emptyList())
            } finally {
                isLoadingLiveData.postValue(false)
            }
        }

    }
}