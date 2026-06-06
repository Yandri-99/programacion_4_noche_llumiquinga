package com.transportapp.data.remote.dto

import com.google.gson.annotations.SerializedName
import com.transportapp.domain.model.Driver
import com.transportapp.domain.model.DriverPayload

data class DriverDto(
    val id: Int,
    val email: String,
    val nombre: String,
    val telefono: String,
    val licencia: String,
    val disponible: Boolean,
    @SerializedName("is_active") val isActive: Boolean,
)

data class DriverRequestDto(
    val email: String,
    val nombre: String,
    val telefono: String,
    val licencia: String,
    val disponible: Boolean,
    @SerializedName("is_active") val isActive: Boolean,
    val password: String? = null,
)

data class ToggleActiveResponseDto(
    val message: String,
    @SerializedName("is_active") val isActive: Boolean,
)

data class DriverStatsDto(
    val total: Int,
    @SerializedName("available") val available: Int,
    @SerializedName("unavailable") val unavailable: Int,
)

fun DriverDto.toDomain() = Driver(
    id = id,
    email = email,
    nombre = nombre,
    telefono = telefono,
    licencia = licencia,
    disponible = disponible,
    isActive = isActive,
)

fun DriverPayload.toRequest() = DriverRequestDto(
    email = email,
    nombre = nombre,
    telefono = telefono,
    licencia = licencia,
    disponible = disponible,
    isActive = isActive,
    password = password,
)
