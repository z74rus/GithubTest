package com.example.githubconductor.mvp.models

import com.example.githubconductor.data.network.GithubApi
import com.example.githubconductor.models.EditUser
import javax.inject.Inject

class EditUserInfoRepo @Inject constructor(private val githubApi: GithubApi) {
    suspend fun editUser(
        editUser: EditUser
    ) {
        githubApi.editInfo(editUser)
    }
}