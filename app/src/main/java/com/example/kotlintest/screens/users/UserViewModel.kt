package com.example.kotlintest.screens.users

import android.annotation.SuppressLint
import android.os.Trace
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlintest.data.source.network.ApiService
import com.example.kotlintest.data.source.network.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val apiService: ApiService,
    private val defaultDispatcher: CoroutineDispatcher
) : ViewModel() {
    private val _response = MutableStateFlow<ApiResult<List<User>>>(ApiResult.Loading())
    val response = _response.asStateFlow()

    init {
        getUsers()
    }

    private fun getUsers() {
        viewModelScope.launch(Dispatchers.IO) {
            apiService
                .getUsers()
                .flowOn(Dispatchers.IO)//defaultDispatcher
                .catch { _response.value = ApiResult.Error(it.message ?: "Something went wrong") }
                .collect {
                    _response.value = it
                }

        }
    }
}

sealed class ApiResult<T>(val data: T? = null, val error: String? = null) {
    class Success<T>(quotes: T) : ApiResult<T>(data = quotes)
    class Error<T>(error: String) : ApiResult<T>(error = error)
    class Loading<T> : ApiResult<T>()
}

