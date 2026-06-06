package com.transportapp.domain.model

data class Route(
    val id: Int,
    val name: String,
    val description: String,
    val origin: String,
    val destination: String,
    val tarifa: Double,
    val image: String?,
)

data class RoutePayload(
    val name: String,
    val description: String,
    val origin: String,
    val destination: String,
    val tarifa: Double,
    val image: String?,
)
