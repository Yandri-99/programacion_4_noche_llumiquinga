package com.transportapp.domain.model

data class Driver(
    val id: Int,
    val username: String,
    val email: String,
    val firstName: String,
    val lastName: String,
    val isStaff: Boolean,
    val isActive: Boolean,
    val dateJoined: String,
    val numTrips: Int,
)

data class DriverPayload(
    val username: String,
    val email: String,
    val firstName: String,
    val lastName: String,
    val isStaff: Boolean,
    val isActive: Boolean,
    val password: String? = null,
)
