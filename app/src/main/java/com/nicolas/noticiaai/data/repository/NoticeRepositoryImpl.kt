package com.nicolas.noticiaai.data.repository

import com.nicolas.noticiaai.data.datasource.remote.NoticeRemoteDataSource
import com.nicolas.noticiaai.data.mapper.toNoticeUiDomain
import com.nicolas.noticiaai.domain.model.NoticeUiDomain
import com.nicolas.noticiaai.domain.repository.NoticeRepository
import javax.inject.Inject

class NoticeRepositoryImpl @Inject constructor(
    private val remote: NoticeRemoteDataSource
) : NoticeRepository {

    override suspend fun fetchNoticeSports(): List<NoticeUiDomain> {
        val response = remote.getNoticeSports()
        return response.toNoticeUiDomain(response.articles)
    }

    override suspend fun fetchNoticeTechnology(): List<NoticeUiDomain> {
        val response = remote.getNoticeTechnology()
        return response.toNoticeUiDomain(response.articles)
    }

    override suspend fun fetchNoticeScience(): List<NoticeUiDomain> {
        val response = remote.getNoticeScience()
        return response.toNoticeUiDomain(response.articles)
    }
}