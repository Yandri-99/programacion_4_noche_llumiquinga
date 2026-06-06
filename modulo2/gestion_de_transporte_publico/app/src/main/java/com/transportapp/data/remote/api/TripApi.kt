package com.transportapp.data.remote.api

import com.transportapp.data.remote.dto.*
import retrofit2.Response
import retrofit2.http.*

interface TripApi {
    @GET("trips/")
    suspend fun getTrips(
        @Query("page")   page:   Int?    = null,
        @Query("estado") status: String? = null,
    ): Response<PaginatedDto<TripDto>>

    @GET("trips/{id}/")
    suspend fun getTrip(@Path("id") id: Int): Response<TripDto>

    @POST("trips/")
    suspend fun createTrip(@Body body: CreateTripRequestDto): Response<TripDto>

    @PATCH("trips/{id}/")
    suspend fun updateTrip(
        @Path("id") id: Int,
        @Body body: CreateTripRequestDto,
    ): Response<TripDto>

    @DELETE("trips/{id}/")
    suspend fun deleteTrip(@Path("id") id: Int): Response<Unit>

    @POST("trips/{id}/update-status/")
    suspend fun updateTripStatus(
        @Path("id") id: Int,
        @Body body: UpdateTripStatusRequestDto,
    ): Response<TripDto>

    @GET("trips/stats/")
    suspend fun getStats(): Response<TripStatsDto>
}
