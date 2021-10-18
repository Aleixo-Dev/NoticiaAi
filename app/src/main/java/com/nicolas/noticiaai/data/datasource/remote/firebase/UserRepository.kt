package com.nicolas.noticiaai.data.datasource.remote.firebase

import android.net.Uri
import com.nicolas.noticiaai.common.User
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val dataSource: UserDataSource
) {

    suspend fun getUser() = dataSource.getUser()

    suspend fun uploadUserImage(imageUri: Uri) = dataSource.uploadUserImage(imageUri)

    suspend fun createUser(user: User) = dataSource.createUser(user)

    suspend fun fetchIdCurrentUser() = dataSource.fetchIdCurrentUser()

}