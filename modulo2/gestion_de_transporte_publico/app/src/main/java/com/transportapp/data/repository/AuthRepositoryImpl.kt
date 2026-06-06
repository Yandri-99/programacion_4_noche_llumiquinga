package com.transportapp.data.repository

import com.transportapp.data.local.TokenDataStore
import com.transportapp.data.remote.api.AuthApi
import com.transportapp.data.remote.dto.*
import com.transportapp.domain.model.LoggedDriver
import com.transportapp.domain.repository.AuthRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val api: AuthApi,
    private val tokenDataStore: TokenDataStore,
) : AuthRepository {

    override suspend fun login(email: String, password: String): Result<LoggedDriver> =
        runCatching {
            val response = api.login(LoginRequest(email, password))
            if (!response.isSuccessful) {
                val errorBody = response.errorBody()?.string() ?: ""
                error(parseErrorMessage(errorBody, response.code()))
            }
            val body = response.body()!!
            tokenDataStore.saveTokens(body.access, body.refresh)
            tokenDataStore.saveUser(body.userId, body.nombre, body.email, body.isStaff)
            LoggedDriver(body.userId, body.email, body.nombre, body.isStaff)
        }

    override suspend fun register(
        nombre: String,
        email: String,
        password: String,
        password2: String,
    ): Result<LoggedDriver> = runCatching {
        val response = api.register(RegisterRequest(email, nombre, password, password2))
        if (!response.isSuccessful) {
            val errorBody = response.errorBody()?.string() ?: ""
            error(parseErrorMessage(errorBody, response.code()))
        }
        val body = response.body()!!
        tokenDataStore.saveTokens(body.access, body.refresh)
        tokenDataStore.saveUser(body.userId, body.nombre, body.email, body.isStaff)
        LoggedDriver(body.userId, body.email, body.nombre, body.isStaff)
    }

    override suspend fun logout(): Result<Unit> = runCatching {
        val refresh = tokenDataStore.getRefreshToken()
        if (refresh != null) {
            runCatching { api.logout(LogoutRequest(refresh)) }
        }
        tokenDataStore.clearSession()
    }

    override suspend fun getStoredDriver(): TokenDataStore.UserSnapshot? =
        tokenDataStore.userSnapshot.first()

    override suspend fun isLoggedIn(): Boolean =
        !tokenDataStore.getAccessToken().isNullOrBlank()

    private fun parseErrorMessage(body: String, code: Int): String {
        return try {
            val map = com.google.gson.Gson()
                .fromJson(body, Map::class.java)
            map["detail"]?.toString()
                ?: map["non_field_errors"]?.toString()
                ?: map.values.firstOrNull()?.toString()
                ?: "Error $code"
        } catch (e: Exception) {
            "Error $code"
        }
    }
}
