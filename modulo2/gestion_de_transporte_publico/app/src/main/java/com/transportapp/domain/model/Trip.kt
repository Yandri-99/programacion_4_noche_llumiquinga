package com.transportapp.domain.model

enum class TripStatus(val value: String, val label: String) {
    PENDIENTE("pendiente", "Pendiente"),
    EN_CURSO("en_curso", "En curso"),
    COMPLETADO("completado", "Completado"),
    CANCELADO("cancelado", "Cancelado");

    companion object {
        fun fromValue(value: String): TripStatus =
            entries.firstOrNull { it.value == value } ?: PENDIENTE
    }
}

data class Trip(
    val id: Int,
    val driverId: Int,
    val driverName: String?,
    val vehicleId: Int,
    val vehiclePlaca: String?,
    val routeId: Int,
    val routeName: String?,
    val origin: String,
    val destination: String,
    val pasajeros: Int,
    val total: Double,
    val estado: TripStatus,
    val fecha: String,
    val createdAt: String,
    val updatedAt: String?,
)
