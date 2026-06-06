package com.transportapp.data.repository

import com.transportapp.data.remote.api.VehicleApi
import com.transportapp.data.remote.dto.toDomain
import com.transportapp.data.remote.dto.toRequest
import com.transportapp.domain.model.Vehicle
import com.transportapp.domain.model.VehicleFilters
import com.transportapp.domain.model.VehiclePayload
import com.transportapp.domain.repository.VehicleRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VehicleRepositoryImpl @Inject constructor(
    private val api: VehicleApi,
) : VehicleRepository {

    override suspend fun getVehicles(filters: VehicleFilters): Result<Pair<List<Vehicle>, Int>> =
        runCatching {
            val params = buildMap<String, String> {
                filters.search?.let    { put("search",       it) }
                filters.tipo?.let      { put("tipo",         it) }
                filters.estado?.let    { put("estado",       it) }
                filters.capacidadMin?.let { put("capacidad_min", it.toString()) }
                filters.capacidadMax?.let { put("capacidad_max", it.toString()) }
                filters.ordering?.let  { put("ordering",     it) }
                put("page",      filters.page.toString())
                put("page_size", filters.pageSize.toString())
            }
            val response = api.getVehicles(params)
            if (response.isSuccessful) {
                val body = response.body()!!
                Pair(body.results.map { it.toDomain() }, body.count)
            } else error("Error ${response.code()}")
        }

    override suspend fun getVehicle(id: Int): Result<Vehicle> = runCatching {
        val response = api.getVehicle(id)
        if (response.isSuccessful) response.body()!!.toDomain()
        else error("Error ${response.code()}")
    }

    override suspend fun createVehicle(payload: VehiclePayload): Result<Vehicle> = runCatching {
        val response = api.createVehicle(payload.toRequest())
        if (response.isSuccessful) response.body()!!.toDomain()
        else error("Error ${response.code()}: ${response.errorBody()?.string()}")
    }

    override suspend fun updateVehicle(id: Int, payload: VehiclePayload): Result<Vehicle> =
        runCatching {
            val response = api.updateVehicle(id, payload.toRequest())
            if (response.isSuccessful) response.body()!!.toDomain()
            else error("Error ${response.code()}: ${response.errorBody()?.string()}")
        }

    override suspend fun deleteVehicle(id: Int): Result<Unit> = runCatching {
        val response = api.deleteVehicle(id)
        if (!response.isSuccessful) error("Error ${response.code()}")
    }

    override suspend fun getStats(): Result<Map<String, Any>> = runCatching {
        val response = api.getStats()
        if (response.isSuccessful) {
            val s = response.body()!!
            mapOf(
                "total_active"   to s.totalActive,
                "total_inactive" to s.totalInactive,
                "avg_capacidad"  to (s.avgCapacidad ?: 0.0),
                "by_tipo"        to s.byTipo,
            )
        } else error("Error ${response.code()}")
    }
}
