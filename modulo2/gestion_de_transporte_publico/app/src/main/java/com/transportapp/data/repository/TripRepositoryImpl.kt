package com.transportapp.data.repository

import com.transportapp.data.remote.api.TripApi
import com.transportapp.data.remote.dto.CreateTripRequestDto
import com.transportapp.data.remote.dto.UpdateTripStatusRequestDto
import com.transportapp.data.remote.dto.toDomain
import com.transportapp.domain.model.Trip
import com.transportapp.domain.model.TripStatus
import com.transportapp.domain.repository.TripRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TripRepositoryImpl @Inject constructor(
    private val api: TripApi,
) : TripRepository {

    override suspend fun getTrips(page: Int?, status: String?): Result<Pair<List<Trip>, Int>> =
        runCatching {
            val response = api.getTrips(page = page, status = status)
            if (response.isSuccessful) {
                val body = response.body()!!
                Pair(body.results.map { it.toDomain() }, body.count)
            } else error("Error ${response.code()}")
        }

    override suspend fun getTrip(id: Int): Result<Trip> = runCatching {
        val response = api.getTrip(id)
        if (response.isSuccessful) response.body()!!.toDomain()
        else error("Error ${response.code()}")
    }

    override suspend fun createTrip(origin: String, destination: String, pasajeros: Int, total: Double, routeId: Int?, vehicleId: Int?): Result<Trip> = runCatching {
        val response = api.createTrip(CreateTripRequestDto(origin, destination, pasajeros, total, routeId, vehicleId))
        if (response.isSuccessful) response.body()!!.toDomain()
        else error("Error ${response.code()}: ${response.errorBody()?.string()}")
    }

    override suspend fun updateTrip(id: Int, origin: String, destination: String, pasajeros: Int, total: Double, routeId: Int?, vehicleId: Int?): Result<Trip> = runCatching {
        val response = api.updateTrip(id, CreateTripRequestDto(origin, destination, pasajeros, total, routeId, vehicleId))
        if (response.isSuccessful) response.body()!!.toDomain()
        else error("Error ${response.code()}: ${response.errorBody()?.string()}")
    }

    override suspend fun deleteTrip(id: Int): Result<Unit> = runCatching {
        val response = api.deleteTrip(id)
        if (!response.isSuccessful) error("Error ${response.code()}")
    }

    override suspend fun updateStatus(tripId: Int, status: TripStatus): Result<Trip> =
        runCatching {
            val response = api.updateTripStatus(tripId, UpdateTripStatusRequestDto(status.value))
            if (response.isSuccessful) response.body()!!.toDomain()
            else error("Error ${response.code()}")
        }

    override suspend fun getStats(): Result<Map<String, Any>> = runCatching {
        val response = api.getStats()
        if (response.isSuccessful) {
            val s = response.body()!!
            mapOf<String, Any>(
                "total_trips"   to s.totalTrips,
                "total_revenue" to (s.totalRevenue.toDoubleOrNull() ?: 0.0),
                "by_status"     to s.byStatus,
            )
        } else error("Error ${response.code()}")
    }
}
