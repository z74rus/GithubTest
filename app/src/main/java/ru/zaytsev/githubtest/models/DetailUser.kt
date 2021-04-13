package ru.zaytsev.githubtest.models

import androidx.room.Entity
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlin.random.Random
@Entity
@JsonClass(generateAdapter = true)
data class DetailUser(
    @Json(name = "login")
    val userName: String = "",
    val name: String = "",
    @Json(name = "avatar_url")
    val avatar: String = "",
    val id: Long = Random.nextLong(),
    @Json(name = "followers")
    val followers: Int = 0,
    @Json(name = "following")
    val following: Int = 0,
    val bio: String = ""
)
