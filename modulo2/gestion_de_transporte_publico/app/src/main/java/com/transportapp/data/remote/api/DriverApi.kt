// data/remote/api/DriverApi.kt
package com.transportapp.data.remote.api

import com.transportapp.data.remote.dto.*
import retrofit2.Response
import retrofit2.http.*

interface DriverApi {
    @GET("drivers/")
    suspend fun getDrivers(
        @Query("search")    search:   String?  = null,
        @Query("is_staff")  isStaff:  Boolean? = null,
        @Query("is_active") isActive: Boolean? = null,
        @Query("page")      page:     Int?     = null,
    ): Response<PaginatedDto<DriverDto>>

    @GET("drivers/{id}/")
    suspend fun getDriver(@Path("id") id: Int): Response<DriverDto>

    @POST("drivers/")
    suspend fun createDriver(@Body body: DriverRequestDto): Response<DriverDto>

    @PATCH("drivers/{id}/")
    suspend fun updateDriver(
        @Path("id") id: Int,
        @Body body: DriverRequestDto,
    ): Response<DriverDto>

    @DELETE("drivers/{id}/")
    suspend fun deleteDriver(@Path("id") id: Int): Response<Unit>

    @POST("drivers/{id}/toggle-active/")
    suspend fun toggleActive(@Path("id") id: Int): Response<ToggleActiveResponseDto>

    @GET("drivers/profile/")
    suspend fun getProfile(): Response<DriverDto>

    @GET("drivers/stats/")
    suspend fun getStats(): Response<DriverStatsDto>
}
