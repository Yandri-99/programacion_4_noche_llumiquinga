// domain/repository/AuthRepository.kt
package com.shopapp.domain.repository

import com.shopapp.data.local.TokenDataStore
import com.shopapp.domain.model.LoggedUser

interface AuthRepository {
    suspend fun login(username: String, password: String): Result<LoggedUser>
    suspend fun register(
        username: String,
        email: String,
        password: String,
        password2: String,
    ): Result<LoggedUser>
    suspend fun logout(): Result<Unit>
    suspend fun getStoredUser(): TokenDataStore.UserSnapshot?
    suspend fun isLoggedIn(): Boolean

    // ── Recuperación de contraseña (13.3) ───────────────────────────────────
    suspend fun requestReset(email: String): Result<String>
    suspend fun confirmReset(
        uid:          String,
        token:        String,
        newPassword:  String,
        newPassword2: String,
    ): Result<String>
}