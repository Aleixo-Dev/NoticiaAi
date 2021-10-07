package com.nicolas.noticiaai.data.model


import com.google.gson.annotations.SerializedName

data class NoticeResponse(
    @SerializedName("articles")
    val articles: List<Article>,
    @SerializedName("status")
    val status: String,
    @SerializedName("totalResults")
    val totalResults: Int
)