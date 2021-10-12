package com.nicolas.noticiaai.data.datasource.remote.firebase

import android.net.Uri
import com.nicolas.noticiaai.common.User

interface UserDataSource {

    suspend fun getUser(): List<User>

    suspend fun uploadUserImage(imageUri: Uri): String

    suspend fun createUser(user: User): User

}