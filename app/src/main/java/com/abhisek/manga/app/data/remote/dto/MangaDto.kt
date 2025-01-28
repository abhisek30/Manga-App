package com.abhisek.manga.app.data.remote.dto

import com.google.gson.annotations.SerializedName

data class MangaDto(
    @SerializedName("id") val id: String? = null,
    @SerializedName("image") val image: String? = null,
    @SerializedName("score") val score: Double? = null,
    @SerializedName("popularity") val popularity: Int? = null,
    @SerializedName("title") val title: String? = null,
    @SerializedName("publishedChapterDate") val publishedChapterDate: Int? = null,
    @SerializedName("category") val category: String? = null
)
