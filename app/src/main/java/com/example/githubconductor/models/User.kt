package com.example.githubconductor.models

import android.os.Parcelable
import androidx.room.Entity
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

import kotlin.random.Random

@Entity
@Parcelize
@JsonClass(generateAdapter = true)
data class User(
    @Json(name = "login")
    val name: String = "",
    @Json(name = "avatar_url")
    val avatar: String = "",
    val id: Long = Random.nextLong(),
    var countFollows: Int = 0,
    val followers_url: String? = ""
):Parcelable
