package com.example.kotlintest.data.source.network

import com.example.kotlintest.screens.users.ApiResult
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import io.ktor.client.call.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.serialization.json.Json

class ApiServiceImpl @Inject constructor(private val httpClient: HttpClient) : ApiService {
    private val json = Json { ignoreUnknownKeys = true } // Ignore unknown fields

    override suspend fun getUsers(): Flow<ApiResult<List<User>>> =flow{
        emit(ApiResult.Loading())
        try {
            val responseBody: String = httpClient.get("api/v1/users").body() // Get raw JSON as string
            val users: List<User> = json.decodeFromString(responseBody) // Parse JSON to User list
            emit(ApiResult.Success(users))
        }catch (e:Exception){
            e.printStackTrace()
            emit(ApiResult.Error(e.message ?: "Something went wrong"))
        }
    }.flowOn(Dispatchers.IO)
}
