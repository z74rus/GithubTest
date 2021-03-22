package ru.zaytsev.githubtest.data

import ru.zaytsev.githubtest.models.EditUser
import ru.zaytsev.githubtest.networking.Network

class EditUserInfoRepo {
    suspend fun editUser(
        editUser: EditUser
    ) {
        Network.gitHubApi.editInfo(editUser)
    }
}