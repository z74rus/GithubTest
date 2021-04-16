package ru.zaytsev.githubtest.data

import ru.zaytsev.githubtest.models.EditUser
import ru.zaytsev.githubtest.networking.GithubApi
import javax.inject.Inject

class EditUserInfoRepo @Inject constructor(private val githubApi: GithubApi) {
    suspend fun editUser(
        editUser: EditUser
    ) {
        githubApi.editInfo(editUser)
    }
}