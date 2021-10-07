package com.nicolas.noticiaai.data.datasource.remote

import javax.inject.Inject

class NoticeRemoteDataSourceImpl @Inject constructor(
    private val api: NoticeApi
) : NoticeRemoteDataSource {

    override suspend fun getNoticeSports() = api.getNoticeSports()
}