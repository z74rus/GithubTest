package com.example.githubconductor.data.network

import com.example.githubconductor.models.*
import retrofit2.http.*

interface GithubApi {
    @GET("search/users")
    suspend fun searchUsers(
        @Query("per_page") perPage: Int = 10,
        @Query("q") query: String
    ): ServerItemsWrapper<User>

    @GET("users/{username}")
    suspend fun getCurrentUser(
        @Path("username") username: String
    ): DetailUser?

    @GET("/users/{username}/repos")
    suspend fun getRepos(
        @Path("username") username: String
    ): List<Repo>

    @GET
    suspend fun getFollowers(
        @Url followers_url: String
    ): List<User>

    @GET("/user")
    suspend fun getUser(): DetailUser

    @PATCH("/user")
    suspend fun editInfo(
        @Body
        editUser: EditUser
    )
}