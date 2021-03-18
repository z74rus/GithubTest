package ru.zaytsev.githubtest

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize
import kotlin.random.Random


@Parcelize
@JsonClass(generateAdapter = true)
data class User(
    @Json(name = "login")
    val name: String = "",
    @Json(name = "avatar_url")
    val avatar: String = "",
    val id: Long = Random.nextLong(),
):Parcelable
