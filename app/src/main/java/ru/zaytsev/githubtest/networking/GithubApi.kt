package ru.zaytsev.githubtest.networking

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url
import ru.zaytsev.githubtest.DetailUser
import ru.zaytsev.githubtest.Repo
import ru.zaytsev.githubtest.ServerItemsWrapper
import ru.zaytsev.githubtest.User

interface GithubApi {
    @GET("search/users")
    suspend fun searchUsers(
        @Query("q") query: String
    ): ServerItemsWrapper<User>

    @GET("users/{username}")
    suspend fun getUser(
        @Path("username") username: String
    ): DetailUser

    @GET("/users/{username}/repos")
    suspend fun getRepos(
        @Path("username") username: String
    ): List<Repo>
}