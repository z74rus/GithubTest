package com.example.githubconductor.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ServerItemsWrapper<T>(
    val items: List<T>
)