package com.nicolas.noticiaai.data.mapper

import com.nicolas.noticiaai.data.model.Article
import com.nicolas.noticiaai.data.model.NoticeResponse
import com.nicolas.noticiaai.domain.model.NoticeUiDomain

fun NoticeResponse.toNoticeUiDomain(articles: List<Article>): List<NoticeUiDomain> {

    val noticeMapped = ArrayList<NoticeUiDomain>()

    for (notice in articles) {
        val noticeUiDomain = NoticeUiDomain(
            sourceName = notice.source.name,
            title = notice.title,
            description = notice.description,
            urlWebPage = notice.url,
            urlToImage = notice.urlToImage,
            content = notice.content,
            publishedAt = notice.publishedAt
        )

        noticeMapped.add(noticeUiDomain)
    }
    return noticeMapped
}
