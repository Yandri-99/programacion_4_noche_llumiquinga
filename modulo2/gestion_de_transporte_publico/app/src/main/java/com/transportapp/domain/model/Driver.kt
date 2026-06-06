package com.transportapp.domain.model

data class Driver(
    val id: Int,
    val email: String,
    val nombre: String,
    val telefono: String,
    val licencia: String,
    val disponible: Boolean,
    val isActive: Boolean,
)

data class DriverPayload(
    val email: String,
    val nombre: String,
    val telefono: String,
    val licencia: String,
    val disponible: Boolean,
    val isActive: Boolean,
    val password: String? = null,
)
