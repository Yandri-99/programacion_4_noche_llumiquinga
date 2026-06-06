// domain/repository/DriverRepository.kt
package com.transportapp.domain.repository

import com.transportapp.domain.model.Driver
import com.transportapp.domain.model.DriverPayload

interface DriverRepository {
    suspend fun getDrivers(
        search:   String?  = null,
        isActive: Boolean? = null,
        page:     Int?     = null,
    ): Result<Pair<List<Driver>, Int>>
    suspend fun getDriver(id: Int): Result<Driver>
    suspend fun createDriver(payload: DriverPayload): Result<Driver>
    suspend fun updateDriver(id: Int, payload: DriverPayload): Result<Driver>
    suspend fun deleteDriver(id: Int): Result<Unit>
    suspend fun toggleActive(id: Int): Result<Boolean>
    suspend fun getStats(): Result<Map<String, Int>>
}
