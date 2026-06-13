// data/remote/api/AuthApi.kt
package com.shopapp.data.remote.api

import com.shopapp.data.remote.dto.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("auth/login/")
    suspend fun login(@Body body: LoginRequest): Response<AuthResponseDto>

    @POST("auth/register/")
    suspend fun register(@Body body: RegisterRequest): Response<AuthResponseDto>

    @POST("auth/token/refresh/")
    suspend fun refreshToken(@Body body: TokenRefreshRequest): Response<TokenRefreshResponseDto>

    @POST("auth/logout/")
    suspend fun logout(@Body body: LogoutRequest): Response<Unit>

    // ── Recuperación de contraseña (13.2) ───────────────────────────────────
    /** Backend: POST /api/auth/password-reset/ — no requiere autenticación */
    @POST("auth/password-reset/")
    suspend fun requestPasswordReset(
        @Body body: PasswordResetRequestDto,
    ): Response<MessageDto>

    /** Backend: POST /api/auth/password-reset/confirm/ */
    @POST("auth/password-reset/confirm/")
    suspend fun confirmPasswordReset(
        @Body body: PasswordResetConfirmDto,
    ): Response<MessageDto>
}