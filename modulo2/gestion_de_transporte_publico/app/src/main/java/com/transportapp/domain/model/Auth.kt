package com.transportapp.domain.model

data class AuthTokens(
    val access: String,
    val refresh: String,
)

data class LoggedDriver(
    val id: Int,
    val email: String,
    val nombre: String,
    val isStaff: Boolean,
)
