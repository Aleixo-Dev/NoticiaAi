package com.nicolas.noticiaai.domain.usecase

import com.nicolas.noticiaai.data.datasource.remote.firebase.UserRepository
import java.lang.Exception
import javax.inject.Inject

class GetCurrentIdUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke() : String{
        return try {
            repository.fetchIdCurrentUser()
        }catch (exception : Exception){
            throw exception
        }
    }
}