package com.nicolas.noticiaai.domain.repository

import com.nicolas.noticiaai.domain.model.NoticeUiDomain

interface NoticeRepository {

    suspend fun fetchNoticeSports(): List<NoticeUiDomain>

    suspend fun fetchNoticeTechnology(): List<NoticeUiDomain>

    suspend fun fetchNoticeScience(): List<NoticeUiDomain>


}