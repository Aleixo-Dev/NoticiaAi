package com.nicolas.noticiaai.domain.usecase

import android.net.Uri
import com.nicolas.noticiaai.common.User
import com.nicolas.noticiaai.data.datasource.remote.firebase.UserRepository
import java.util.*
import javax.inject.Inject

class CreateUserUseCaseImpl @Inject constructor(
    private val userRepository: UserRepository
) : CreateUserUseCase {
    override suspend fun invoke(id: String, name: String, imageUri: Uri): User {
        return try {
            val imageUrl = userRepository.uploadUserImage(imageUri)
            val user = User(id, name, imageUrl)
            userRepository.createUser(user)
        } catch (exception: Exception) {
            throw exception
        }
    }
}