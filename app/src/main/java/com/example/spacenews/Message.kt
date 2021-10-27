package com.example.spacenews

import com.google.gson.annotations.SerializedName

data class Message(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("url") val url: String,
    @SerializedName("imageUrl") val imageUrl: String,
    @SerializedName("newsSite") val newsSite: String,
    @SerializedName("summary") val summary: String,
    @SerializedName("publishedAt") val publishedAt: String,
    @SerializedName("updatedAt") val updatedAt: String,
    @SerializedName("featured") val featured: Boolean,
    @SerializedName("launches") val launches: Array<String>,
    @SerializedName("events") val events: Array<String>
)