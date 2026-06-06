// domain/repository/RouteRepository.kt
package com.transportapp.domain.repository

import com.transportapp.domain.model.Route
import com.transportapp.domain.model.RoutePayload

interface RouteRepository {
    suspend fun getRoutes(): Result<List<Route>>
    suspend fun getRoute(id: Int): Result<Route>
    suspend fun createRoute(payload: RoutePayload): Result<Route>
    suspend fun updateRoute(id: Int, payload: RoutePayload): Result<Route>
    suspend fun deleteRoute(id: Int): Result<Unit>
    suspend fun getStats(): Result<Map<String, Any>>
}
