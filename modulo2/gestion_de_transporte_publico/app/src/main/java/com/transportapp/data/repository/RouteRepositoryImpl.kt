// data/repository/RouteRepositoryImpl.kt
package com.transportapp.data.repository

import com.transportapp.data.remote.api.RouteApi
import com.transportapp.data.remote.dto.toDomain
import com.transportapp.data.remote.dto.toRequest
import com.transportapp.domain.model.Route
import com.transportapp.domain.model.RoutePayload
import com.transportapp.domain.repository.RouteRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RouteRepositoryImpl @Inject constructor(
    private val api: RouteApi,
) : RouteRepository {

    override suspend fun getRoutes(): Result<List<Route>> = runCatching {
        val response = api.getRoutes()
        if (response.isSuccessful) {
            response.body()!!.results.map { it.toDomain() }
        } else {
            error("Error ${response.code()}: ${response.errorBody()?.string()}")
        }
    }

    override suspend fun getRoute(id: Int): Result<Route> = runCatching {
        val response = api.getRoute(id)
        if (response.isSuccessful) response.body()!!.toDomain()
        else error("Error ${response.code()}")
    }

    override suspend fun createRoute(payload: RoutePayload): Result<Route> = runCatching {
        val response = api.createRoute(payload.toRequest())
        if (response.isSuccessful) response.body()!!.toDomain()
        else error("Error ${response.code()}: ${response.errorBody()?.string()}")
    }

    override suspend fun updateRoute(id: Int, payload: RoutePayload): Result<Route> =
        runCatching {
            val response = api.updateRoute(id, payload.toRequest())
            if (response.isSuccessful) response.body()!!.toDomain()
            else error("Error ${response.code()}: ${response.errorBody()?.string()}")
        }

    override suspend fun deleteRoute(id: Int): Result<Unit> = runCatching {
        val response = api.deleteRoute(id)
        if (!response.isSuccessful) {
            error("Error ${response.code()}: ${response.errorBody()?.string()}")
        }
    }

    override suspend fun getStats(): Result<Map<String, Any>> = runCatching {
        val response = api.getStats()
        if (response.isSuccessful) {
            val s = response.body()!!

            mapOf(
                "total"    to s.total,
                "active"   to s.active,
                "inactive" to s.inactive,
                "detail"   to s.detail
            )
        } else {
            error("Error ${response.code()}: ${response.errorBody()?.string()}")
        }
    }
}
