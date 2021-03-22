package ru.zaytsev.githubtest.ui.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import ru.zaytsev.githubtest.data.SearchUsersRepository
import ru.zaytsev.githubtest.models.User


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
                val usersResult = async {  repository.searchUsers(query) }
                val users = usersResult.await()
                users.forEach { user ->
                    val listFollowsResult = async { repository.getFollowers(user.followers_url ?: "") }
                    val listFollowers = listFollowsResult.await()
                    user.countFollows = listFollowers.size ?: 0
                }
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