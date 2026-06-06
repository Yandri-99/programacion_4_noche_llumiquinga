// data/remote/api/RouteApi.kt
package com.transportapp.data.remote.api

import com.transportapp.data.remote.dto.*
import retrofit2.Response
import retrofit2.http.*

interface RouteApi {
    @GET("routes/")
    suspend fun getRoutes(): Response<PaginatedDto<RouteDto>>

    @GET("routes/{id}/")
    suspend fun getRoute(@Path("id") id: Int): Response<RouteDto>

    @POST("routes/")
    suspend fun createRoute(@Body body: RouteRequestDto): Response<RouteDto>

    @PATCH("routes/{id}/")
    suspend fun updateRoute(
        @Path("id") id: Int,
        @Body body: RouteRequestDto,
    ): Response<RouteDto>

    @DELETE("routes/{id}/")
    suspend fun deleteRoute(@Path("id") id: Int): Response<Unit>

    @GET("routes/stats/")
    suspend fun getStats(): Response<RouteStatsDto>
}
