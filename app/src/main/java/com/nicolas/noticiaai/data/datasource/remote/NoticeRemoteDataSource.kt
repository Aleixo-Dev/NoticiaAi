package com.nicolas.noticiaai.data.datasource.remote

import com.nicolas.noticiaai.data.model.NoticeResponse

interface NoticeRemoteDataSource {

    suspend fun getNoticeSports() : NoticeResponse

}