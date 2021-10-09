package com.nicolas.noticiaai.domain.model

data class NoticeUiDomain(
    val sourceName: String,
    val title: String,
    val description: String? = null,
    val urlWebPage: String,
    val urlToImage: String? = null,
    val content: String? = null,
    val publishedAt: String
)