package com.transportapp.domain.repository

import com.transportapp.domain.model.Trip
import com.transportapp.domain.model.TripStatus

interface TripRepository {
    suspend fun getTrips(page: Int? = null, status: String? = null): Result<Pair<List<Trip>, Int>>
    suspend fun getTrip(id: Int): Result<Trip>
    suspend fun createTrip(origin: String, destination: String, pasajeros: Int, total: Double, routeId: Int?, vehicleId: Int?): Result<Trip>
    suspend fun updateTrip(id: Int, origin: String, destination: String, pasajeros: Int, total: Double, routeId: Int?, vehicleId: Int?): Result<Trip>
    suspend fun deleteTrip(id: Int): Result<Unit>
    suspend fun updateStatus(tripId: Int, status: TripStatus): Result<Trip>
    suspend fun getStats(): Result<Map<String, Any>>
}
