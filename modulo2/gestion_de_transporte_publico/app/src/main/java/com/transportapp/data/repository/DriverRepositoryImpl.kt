package com.transportapp.data.repository

import com.transportapp.data.remote.api.DriverApi
import com.transportapp.data.remote.dto.DriverRequestDto
import com.transportapp.data.remote.dto.toDomain
import com.transportapp.data.remote.dto.toRequest
import com.transportapp.domain.model.Driver
import com.transportapp.domain.model.DriverPayload
import com.transportapp.domain.repository.DriverRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DriverRepositoryImpl @Inject constructor(
    private val api: DriverApi,
) : DriverRepository {

    override suspend fun getDrivers(
        search:   String?,
        isActive: Boolean?,
        page:     Int?,
    ): Result<Pair<List<Driver>, Int>> = runCatching {
        val response = api.getDrivers(search = search, isActive = isActive, page = page)
        if (response.isSuccessful) {
            val body = response.body()!!
            Pair(body.results.map { it.toDomain() }, body.count)
        } else error("Error ${response.code()}")
    }

    override suspend fun getDriver(id: Int): Result<Driver> = runCatching {
        val response = api.getDriver(id)
        if (response.isSuccessful) response.body()!!.toDomain()
        else error("Error ${response.code()}")
    }

    override suspend fun createDriver(payload: DriverPayload): Result<Driver> = runCatching {
        val response = api.createDriver(payload.toRequest())
        if (response.isSuccessful) response.body()!!.toDomain()
        else error("Error ${response.code()}: ${response.errorBody()?.string()}")
    }

    override suspend fun updateDriver(id: Int, payload: DriverPayload): Result<Driver> = runCatching {
        val response = api.updateDriver(id, payload.toRequest())
        if (response.isSuccessful) response.body()!!.toDomain()
        else error("Error ${response.code()}: ${response.errorBody()?.string()}")
    }

    override suspend fun deleteDriver(id: Int): Result<Unit> = runCatching {
        val response = api.deleteDriver(id)
        if (!response.isSuccessful) error("Error ${response.code()}")
    }

    override suspend fun toggleActive(id: Int): Result<Boolean> = runCatching {
        val response = api.toggleActive(id)
        if (response.isSuccessful) response.body()!!.isActive
        else error("Error ${response.code()}")
    }

    override suspend fun getStats(): Result<Map<String, Int>> = runCatching {
        val response = api.getStats()
        if (response.isSuccessful) {
            val s = response.body()!!
            mapOf(
                "total"       to s.total,
                "active"      to s.available,
                "inactive"    to s.unavailable,
            )
        } else error("Error ${response.code()}")
    }
}
