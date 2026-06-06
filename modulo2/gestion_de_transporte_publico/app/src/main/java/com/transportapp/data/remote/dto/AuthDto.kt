package com.transportapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    val email: String,
    val password: String,
)

data class RegisterRequest(
    val email: String,
    val nombre: String,
    val password: String,
    @SerializedName("password2") val password2: String,
)

data class TokenRefreshRequest(
    val refresh: String,
)

data class LogoutRequest(
    val refresh: String,
)

data class AuthResponseDto(
    val access: String,
    val refresh: String,
    @SerializedName("user_id") val userId: Int,
    val email: String,
    val nombre: String,
    @SerializedName("is_staff") val isStaff: Boolean,
)

data class TokenRefreshResponseDto(
    val access: String,
    val refresh: String?,
)
