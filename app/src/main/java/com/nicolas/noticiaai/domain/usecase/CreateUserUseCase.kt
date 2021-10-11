package com.nicolas.noticiaai.domain.usecase

import android.net.Uri
import com.nicolas.noticiaai.common.User

interface CreateUserUseCase {

    suspend operator fun invoke(id : String,name: String, imageUri: Uri): User

}