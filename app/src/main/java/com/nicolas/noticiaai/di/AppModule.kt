package com.nicolas.noticiaai.di

import com.nicolas.noticiaai.data.datasource.remote.NoticeApi
import com.nicolas.noticiaai.data.datasource.remote.NoticeRemoteDataSource
import com.nicolas.noticiaai.data.datasource.remote.NoticeRemoteDataSourceImpl
import com.nicolas.noticiaai.data.repository.NoticeRepositoryImpl
import com.nicolas.noticiaai.domain.repository.NoticeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDataSource(api: NoticeApi): NoticeRemoteDataSource {
        return NoticeRemoteDataSourceImpl(api)
    }

    @Provides
    @Singleton
    fun provideRepository(remote: NoticeRemoteDataSource): NoticeRepository {
        return NoticeRepositoryImpl(remote)
    }
}