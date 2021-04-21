package com.example.githubmvp.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class EditUser(
    val name: String,
    val location: String,
    val bio: String
)