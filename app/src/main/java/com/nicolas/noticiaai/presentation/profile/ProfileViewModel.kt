package com.nicolas.noticiaai.presentation.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nicolas.noticiaai.common.User
import com.nicolas.noticiaai.domain.usecase.GetCurrentIdUseCase
import com.nicolas.noticiaai.domain.usecase.GetUsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getCurrentIdUseCase: GetCurrentIdUseCase,
    private val getUsersUseCase: GetUsersUseCase
) : ViewModel() {

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    private val _currentIdUser = MutableLiveData<String>()
    val currentIdUser: LiveData<String> = _currentIdUser

    init {
        fetchCurrentIdUser()
        getUser()
    }

    private fun getUser() = viewModelScope.launch {
        try {
            val currentUserId = getCurrentIdUseCase.invoke()
            val users = getUsersUseCase.invoke()

            for (i in users) {
                if (currentUserId == i.id) {
                    _user.value = User(name = i.name, image = i.image)
                }
            }
        } catch (exception: Exception) {

        }
    }

    private fun fetchCurrentIdUser() = viewModelScope.launch {
        _currentIdUser.value = getCurrentIdUseCase.invoke()
    }
}