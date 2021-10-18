package com.nicolas.noticiaai.domain.usecase

import com.nicolas.noticiaai.common.User
import com.nicolas.noticiaai.data.datasource.remote.firebase.UserRepository
import javax.inject.Inject

class GetUsersUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(): List<User> {
        return repository.getUser()
    }
}