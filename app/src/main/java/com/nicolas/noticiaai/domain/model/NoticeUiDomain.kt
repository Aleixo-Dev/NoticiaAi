package com.nicolas.noticiaai.domain.model

data class NoticeUiDomain(
    val sourceName: String,
    val title: String,
    val description: String,
    val urlWebPage: String,
    val urlToImage: String,
    val content: String? = null,
    val publishedAt: String
)