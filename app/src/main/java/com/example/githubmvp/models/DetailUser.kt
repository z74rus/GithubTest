package com.example.githubmvp.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize
import kotlin.random.Random
@Parcelize
@Entity
@JsonClass(generateAdapter = true)
data class DetailUser(
    @Json(name = "login")
    val userName: String = "",
    val name: String = "",
    @Json(name = "avatar_url")
    val avatar: String? = "",
    @PrimaryKey
    val id: Long = Random.nextLong(),
    @Json(name = "followers")
    val followers: Int = 0,
    @Json(name = "following")
    val following: Int = 0,
    val bio: String = ""
): Parcelable
