// data/remote/api/OrderApi.kt
package com.shopapp.data.remote.api

import com.shopapp.data.remote.dto.*
import retrofit2.Response
import retrofit2.http.*

interface OrderApi {
    @GET("orders/")
    suspend fun getOrders(
        @Query("page")   page:   Int?    = null,
        @Query("status") status: String? = null,
    ): Response<PaginatedDto<OrderDto>>

    @GET("orders/{id}/")
    suspend fun getOrder(@Path("id") id: Int): Response<OrderDto>

    @POST("orders/")
    suspend fun createOrder(): Response<OrderDto>

    @POST("orders/{id}/add-item/")
    suspend fun addItem(
        @Path("id") id: Int,
        @Body body: AddItemRequestDto,
    ): Response<OrderDto>

    @POST("orders/{id}/confirm/")
    suspend fun confirmOrder(@Path("id") id: Int): Response<OrderDto>

    @POST("orders/{id}/update-status/")
    suspend fun updateStatus(
        @Path("id") id: Int,
        @Body body: UpdateStatusRequestDto,
    ): Response<OrderDto>

    @GET("orders/stats/")
    suspend fun getStats(): Response<OrderStatsDto>
}