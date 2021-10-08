package com.nicolas.noticiaai.data.datasource.remote

import javax.inject.Inject

class NoticeRemoteDataSourceImpl @Inject constructor(
    private val api: NoticeApi
) : NoticeRemoteDataSource {

    override suspend fun getNoticeSports() = api.getNoticeSports()

    override suspend fun getNoticeTechnology() = api.getNoticeTechnology()

    override suspend fun getNoticeScience() = api.getNoticeScience()
}