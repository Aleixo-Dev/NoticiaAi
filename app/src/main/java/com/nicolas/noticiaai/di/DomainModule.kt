package com.nicolas.noticiaai.di

import com.nicolas.noticiaai.domain.usecase.CreateUserUseCase
import com.nicolas.noticiaai.domain.usecase.CreateUserUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface DomainModule {

    @Binds
    fun bindCreateUserUseCase(useCase: CreateUserUseCaseImpl): CreateUserUseCase
}