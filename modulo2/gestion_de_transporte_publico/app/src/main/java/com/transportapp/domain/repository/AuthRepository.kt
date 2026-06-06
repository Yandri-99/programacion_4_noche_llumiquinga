package com.transportapp.domain.repository

import com.transportapp.data.local.TokenDataStore
import com.transportapp.domain.model.LoggedDriver

interface AuthRepository {
    suspend fun login(email: String, password: String): Result<LoggedDriver>
    suspend fun register(
        nombre: String,
        email: String,
        password: String,
        password2: String,
    ): Result<LoggedDriver>
    suspend fun logout(): Result<Unit>
    suspend fun getStoredDriver(): TokenDataStore.UserSnapshot?
    suspend fun isLoggedIn(): Boolean
}
