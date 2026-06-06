package com.transportapp.data.remote.dto

import com.google.gson.annotations.SerializedName
import com.transportapp.domain.model.Route
import com.transportapp.domain.model.RoutePayload

data class RouteDto(
    val id: Int,
    val name: String,
    val description: String,
    val origin: String,
    val destination: String,
    val tarifa: String,
    val image: String?,
    @SerializedName("is_active") val isActive: Boolean?,
    @SerializedName("created_at") val createdAt: String?,
    @SerializedName("num_vehicles") val numVehicles: Int?,
)

data class RouteRequestDto(
    val name: String,
    val description: String,
    val origin: String,
    val destination: String,
    val tarifa: Double,
    val image: String?,
)

data class RouteStatsDto(
    val total: Int,
    val active: Int,
    val inactive: Int,
    val detail: List<RouteDetailDto>,
)

data class RouteDetailDto(
    val id: Int,
    val name: String,
    @SerializedName("num_vehicles") val numVehicles: Int,
    @SerializedName("is_active") val isActive: Boolean,
)

fun RouteDto.toDomain() = Route(
    id = id,
    name = name,
    description = description,
    origin = origin,
    destination = destination,
    tarifa = tarifa.toDoubleOrNull() ?: 0.0,
    image = image,
)

fun RoutePayload.toRequest() = RouteRequestDto(
    name = name,
    description = description,
    origin = origin,
    destination = destination,
    tarifa = tarifa,
    image = image,
)
