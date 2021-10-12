package com.nicolas.noticiaai.di

import com.nicolas.noticiaai.data.datasource.remote.firebase.FirebaseUserDataSource
import com.nicolas.noticiaai.data.datasource.remote.firebase.UserDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataSourceModule {

    @Singleton
    @Binds
    fun bindUserDataSource(dataSource: FirebaseUserDataSource): UserDataSource
}