package com.transportapp.domain.model

data class RouteType(
    val id: Int,
    val name: String,
    val slug: String,
    val description: String,
    val isActive: Boolean,
    val totalVehicles: Int,
    val createdAt: String,
)

data class RouteTypePayload(
    val name: String,
    val slug: String,
    val description: String,
    val isActive: Boolean,
)
