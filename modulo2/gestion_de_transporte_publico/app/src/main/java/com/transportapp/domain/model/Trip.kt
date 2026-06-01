package com.transportapp.domain.model

enum class TripStatus(val value: String, val label: String) {
    SCHEDULED("scheduled", "Programado"),
    IN_PROGRESS("in_progress", "En curso"),
    COMPLETED("completed", "Completado"),
    CANCELLED("cancelled", "Cancelado");

    companion object {
        fun fromValue(value: String): TripStatus =
            entries.firstOrNull { it.value == value } ?: SCHEDULED
    }
}

data class TripItem(
    val id: Int,
    val vehicleId: Int,
    val vehiclePlate: String,
    val passengers: Int,
    val fare: Double,
    val subtotal: Double,
)

data class Trip(
    val id: Int,
    val driverUsername: String,
    val status: TripStatus,
    val total: Double,
    val numStops: Int,
    val items: List<TripItem>,
    val createdAt: String,
    val updatedAt: String,
)
