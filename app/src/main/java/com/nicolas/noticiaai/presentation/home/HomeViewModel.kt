package com.nicolas.noticiaai.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nicolas.noticiaai.common.Resource
import com.nicolas.noticiaai.domain.model.NoticeUiDomain
import com.nicolas.noticiaai.domain.usecase.GetNoticeSportsUseCase
import com.nicolas.noticiaai.domain.usecase.GetNoticeTechnologyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getNoticeSportsUseCase: GetNoticeSportsUseCase,
    private val getNoticeTechnologyUseCase: GetNoticeTechnologyUseCase
) : ViewModel() {

    private val _sports = MutableLiveData<NoticeUiState>(NoticeUiState.Loading())
    val sports: LiveData<NoticeUiState> get() = _sports

    private val _technology = MutableLiveData<NoticeUiState>(NoticeUiState.Loading())
    val technology: LiveData<NoticeUiState> get() = _technology

    private val _science = MutableLiveData<NoticeUiState>(NoticeUiState.Loading())
    val science: LiveData<NoticeUiState> get() = _science

    init {
        fetchNoticeSports()
        fetchNoticeTechnology()
        fetchNoticeScience()
    }

    private fun fetchNoticeScience() = viewModelScope.launch {
        when (val result = getNoticeTechnologyUseCase.invoke()) {
            is Resource.Loading -> {
                _science.postValue(NoticeUiState.Loading())
            }
            is Resource.Success -> {
                result.data?.let { notices ->
                    _science.postValue(NoticeUiState.Success(notices))
                }
            }
            is Resource.Error -> {
                _science.postValue(NoticeUiState.Error(result.message.toString()))
            }
        }
    }

    private fun fetchNoticeTechnology() = viewModelScope.launch {
        when (val result = getNoticeTechnologyUseCase.invoke()) {
            is Resource.Loading -> {
                _technology.postValue(NoticeUiState.Loading())
            }
            is Resource.Success -> {
                result.data?.let { notices ->
                    _technology.postValue(NoticeUiState.Success(notices))
                }
            }
            is Resource.Error -> {
                _technology.postValue(NoticeUiState.Error(result.message.toString()))
            }
        }
    }

    private fun fetchNoticeSports() = viewModelScope.launch {
        when (val result = getNoticeSportsUseCase.invoke()) {
            is Resource.Loading -> {
                _sports.postValue(NoticeUiState.Loading())
            }
            is Resource.Success -> {
                result.data?.let { notices ->
                    _sports.postValue(NoticeUiState.Success(notices))
                }
            }
            is Resource.Error -> {
                _sports.postValue(NoticeUiState.Error(result.message.toString()))
            }
        }
    }
}

sealed class NoticeUiState {
    class Loading(val isLoading: Boolean = true) : NoticeUiState()
    class Success(val notices: List<NoticeUiDomain>) : NoticeUiState()
    class Error(val error: String) : NoticeUiState()
}