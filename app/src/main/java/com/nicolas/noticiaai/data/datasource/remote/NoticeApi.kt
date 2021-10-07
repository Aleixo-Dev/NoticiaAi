package com.nicolas.noticiaai.data.datasource.remote

import com.nicolas.noticiaai.BuildConfig
import com.nicolas.noticiaai.common.Constants
import com.nicolas.noticiaai.data.model.NoticeResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NoticeApi {

    @GET(Constants.APP_ENDPOINT)
    suspend fun getNoticeSports(
        @Query("apiKey") apiKey: String = BuildConfig.API_KEY,
        @Query("country") country: String = Constants.COUNTRY_BR,
        @Query("category") category: String = Constants.CATEGORY_SPORTS
    ) : NoticeResponse
}