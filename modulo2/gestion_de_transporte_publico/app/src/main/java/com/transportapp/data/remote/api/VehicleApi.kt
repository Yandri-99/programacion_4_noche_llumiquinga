package com.transportapp.data.remote.api

import com.transportapp.data.remote.dto.*
import retrofit2.Response
import retrofit2.http.*

interface VehicleApi {
    @GET("vehicles/")
    suspend fun getVehicles(
        @QueryMap filters: Map<String, String>,
    ): Response<PaginatedDto<VehicleDto>>

    @GET("vehicles/{id}/")
    suspend fun getVehicle(@Path("id") id: Int): Response<VehicleDto>

    @GET("vehicles/available/")
    suspend fun getAvailable(): Response<PaginatedDto<VehicleDto>>

    @POST("vehicles/")
    suspend fun createVehicle(@Body body: VehicleRequestDto): Response<VehicleDto>

    @PATCH("vehicles/{id}/")
    suspend fun updateVehicle(
        @Path("id") id: Int,
        @Body body: VehicleRequestDto,
    ): Response<VehicleDto>

    @DELETE("vehicles/{id}/")
    suspend fun deleteVehicle(@Path("id") id: Int): Response<Unit>

    @GET("vehicles/stats/")
    suspend fun getStats(): Response<VehicleStatsDto>
}
