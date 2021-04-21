package com.example.githubmvp.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Repo(
    val id: Long,
    val name: String? = "",
    @Json(name = "updated_at")
    val updated: String?,
    @Json(name = "forks_count")
    val forksCount: Int = 0,
    val language: String?,
    @Json(name = "stargazers_count")
    val starsCount: Int = 0,
    val description: String?,
    @Json(name = "default_branch")
    val defaultBranch: String?
)
