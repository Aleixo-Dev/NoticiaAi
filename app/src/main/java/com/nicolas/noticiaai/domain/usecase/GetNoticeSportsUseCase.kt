package com.nicolas.noticiaai.domain.usecase

import com.nicolas.noticiaai.common.Resource
import com.nicolas.noticiaai.domain.model.NoticeUiDomain
import com.nicolas.noticiaai.domain.repository.NoticeRepository
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetNoticeSportsUseCase @Inject constructor(
    private val repository: NoticeRepository
) {
    suspend operator fun invoke(): Resource<List<NoticeUiDomain>> {
        return try {
            val listNoticeSports = repository.fetchNoticeSports()
            return if (listNoticeSports.isNotEmpty()) {
                Resource.Success(listNoticeSports)
            } else {
                Resource.Error("List Empty.")
            }
        } catch (e: HttpException) {
            Resource.Error(e.localizedMessage ?: "An unexpected error occurred.")
        } catch (e: IOException) {
            Resource.Error("Couldn't reach server. Check you internet connection.")
        }
    }
}