package com.example.githubconductor.mvp.presenters

import com.example.githubconductor.models.User
import com.example.githubconductor.mvp.models.SearchUsersRepository
import com.example.githubconductor.utils.launchWithErrorHandler
import com.example.githubconductor.mvp.views.SearchUsersView
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
                CoroutineScope(Dispatchers.IO).launch {
                    launch(Dispatchers.Main) {viewState.showLoading(true)}
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
                    launch(Dispatchers.Main) { viewState.showLoading(false) }
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