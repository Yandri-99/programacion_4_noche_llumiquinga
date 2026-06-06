package com.transportapp.data.remote.dto

import com.google.gson.annotations.SerializedName
import com.transportapp.domain.model.Trip
import com.transportapp.domain.model.TripStatus

data class DriverInTripDto(
    val id: Int,
    val nombre: String,
    val email: String,
    val disponible: Boolean,
)

data class VehicleInTripDto(
    val id: Int,
    val placa: String,
    val name: String,
    val tipo: String,
    val capacidad: Int,
)

data class TripDto(
    val id: Int,
    val driver: DriverInTripDto?,
    val vehicle: VehicleInTripDto?,
    val route: RouteSummaryDto?,
    val origin: String,
    val destination: String,
    val pasajeros: Int,
    val total: String,
    val estado: String,
    val fecha: String,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("updated_at") val updatedAt: String?,
    @SerializedName("driver_name") val driverName: String?,
    @SerializedName("vehicle_placa") val vehiclePlaca: String?,
    @SerializedName("route_name") val routeName: String?,
)

data class CreateTripRequestDto(
    val origin: String,
    val destination: String,
    val pasajeros: Int,
    val total: Double,
    val route: Int?,
    val vehicle: Int?,
)

data class UpdateTripStatusRequestDto(
    val estado: String,
)

data class TripStatsDto(
    @SerializedName("total_trips") val totalTrips: Int,
    @SerializedName("total_revenue") val totalRevenue: String,
    @SerializedName("by_status") val byStatus: Map<String, Int>,
)

fun TripDto.toDomain() = Trip(
    id = id,
    driverId = driver?.id ?: 0,
    driverName = driver?.nombre ?: driverName,
    vehicleId = vehicle?.id ?: 0,
    vehiclePlaca = vehicle?.placa ?: vehiclePlaca,
    routeId = route?.id ?: 0,
    routeName = route?.name ?: routeName,
    origin = origin,
    destination = destination,
    pasajeros = pasajeros,
    total = total.toDoubleOrNull() ?: 0.0,
    estado = TripStatus.fromValue(estado),
    fecha = fecha,
    createdAt = createdAt,
    updatedAt = updatedAt,
)
