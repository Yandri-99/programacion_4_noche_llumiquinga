package com.transportapp.domain.repository

import com.transportapp.domain.model.Vehicle
import com.transportapp.domain.model.VehicleFilters
import com.transportapp.domain.model.VehiclePayload

interface VehicleRepository {
    suspend fun getVehicles(filters: VehicleFilters): Result<Pair<List<Vehicle>, Int>>
    suspend fun getVehicle(id: Int): Result<Vehicle>
    suspend fun createVehicle(payload: VehiclePayload): Result<Vehicle>
    suspend fun updateVehicle(id: Int, payload: VehiclePayload): Result<Vehicle>
    suspend fun deleteVehicle(id: Int): Result<Unit>
    suspend fun getStats(): Result<Map<String, Any>>
}
