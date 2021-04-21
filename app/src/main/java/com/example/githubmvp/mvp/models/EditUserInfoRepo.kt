package com.example.githubmvp.mvp.models

import com.example.githubmvp.data.network.GithubApi
import com.example.githubmvp.models.EditUser
import javax.inject.Inject

class EditUserInfoRepo @Inject constructor(private val githubApi: GithubApi) {
    suspend fun editUser(
        editUser: EditUser
    ) {
        githubApi.editInfo(editUser)
    }
}