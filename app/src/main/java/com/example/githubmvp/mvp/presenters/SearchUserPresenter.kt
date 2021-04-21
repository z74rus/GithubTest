package com.example.githubmvp.mvp.presenters

import com.example.githubmvp.models.User
import com.example.githubmvp.mvp.models.SearchUsersRepository
import com.example.githubmvp.mvp.views.SearchUsersView
import com.example.githubmvp.utils.launchWithErrorHandler
import kotlinx.coroutines.*
import moxy.InjectViewState
import moxy.MvpPresenter
import moxy.presenterScope
import javax.inject.Inject
@InjectViewState
class SearchUserPresenter @Inject constructor(
    private val searchUsersRepository: SearchUsersRepository
) : MvpPresenter<SearchUsersView>() {

    fun onSearchButtonClick(query: String) {
        presenterScope.launchWithErrorHandler(
            block = {
                viewState.showLoading(true)
                CoroutineScope(Dispatchers.IO).launch {
                    val usersResult = async {
                        searchUsersRepository.searchUsers(query)
                    }
                    val users = usersResult.await()
                    users.forEach { user ->
                        val listFollowersResult =
                            async { searchUsersRepository.getFollowers(user.followers_url ?: "") }
                        val listFollowers = listFollowersResult.await()
                        user.countFollows = listFollowers.size
                    }
                    viewState.onClickSearch(users)
                }
            }, onError = {
                viewState.showLoading(false)
                viewState.onError("Error = $it")
                viewState.onClickSearch(emptyList())
            }
        )
    }

    fun onClickUser(user: User) {
        viewState.onClickUser(user)
    }
}