package com.transportapp.domain.model

data class Vehicle(
    val id: Int,
    val name: String,
    val description: String,
    val placa: String,
    val tipo: String,
    val capacidad: Int,
    val precioPasaje: Double,
    val estado: String,
    val image: String?,
    val routeId: Int?,
    val routeName: String?,
)

data class VehiclePayload(
    val name: String,
    val description: String,
    val placa: String,
    val tipo: String,
    val capacidad: Int,
    val precioPasaje: Double,
    val estado: String,
    val routeId: Int,
)

data class VehicleFilters(
    val search: String? = null,
    val tipo: String? = null,
    val capacidadMin: Int? = null,
    val capacidadMax: Int? = null,
    val estado: String? = null,
    val ordering: String? = null,
    val page: Int = 1,
    val pageSize: Int = 12,
)
