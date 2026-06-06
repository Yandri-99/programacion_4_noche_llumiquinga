package com.transportapp.data.remote.dto

import com.google.gson.annotations.SerializedName
import com.transportapp.domain.model.Vehicle
import com.transportapp.domain.model.VehiclePayload

data class RouteSummaryDto(
    val id: Int,
    val name: String,
)

data class VehicleDto(
    val id: Int,
    val name: String,
    val description: String,
    val placa: String,
    val tipo: String,
    val capacidad: Int,
    @SerializedName("precio_pasaje") val precioPasaje: String,
    val estado: String,
    val image: String?,
    val route: RouteSummaryDto?,
    @SerializedName("ruta_name") val rutaName: String?,
    @SerializedName("is_active") val isActive: Boolean?,
    @SerializedName("created_at") val createdAt: String?,
    @SerializedName("updated_at") val updatedAt: String?,
)

data class VehicleRequestDto(
    val name: String,
    val description: String,
    val placa: String,
    val tipo: String,
    val capacidad: Int,
    @SerializedName("precio_pasaje") val precioPasaje: Double,
    val estado: String,
    @SerializedName("route_id") val routeId: Int,
)

data class VehicleStatsDto(
    @SerializedName("total_active") val totalActive: Int,
    @SerializedName("total_inactive") val totalInactive: Int,
    @SerializedName("avg_capacidad") val avgCapacidad: Double?,
    @SerializedName("by_tipo") val byTipo: Map<String, Int>,
)

fun VehicleDto.toDomain() = Vehicle(
    id = id,
    name = name,
    description = description,
    placa = placa,
    tipo = tipo,
    capacidad = capacidad,
    precioPasaje = precioPasaje.toDoubleOrNull() ?: 0.0,
    estado = estado,
    image = image,
    routeId = route?.id,
    routeName = route?.name ?: rutaName,
)

fun VehiclePayload.toRequest() = VehicleRequestDto(
    name = name,
    description = description,
    placa = placa,
    tipo = tipo,
    capacidad = capacidad,
    precioPasaje = precioPasaje,
    estado = estado,
    routeId = routeId,
)
