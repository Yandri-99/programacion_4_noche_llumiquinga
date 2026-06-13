// data/remote/api/CategoryApi.kt
package com.shopapp.data.remote.api

import com.shopapp.data.remote.dto.*
import retrofit2.Response
import retrofit2.http.*

interface CategoryApi {
    @GET("categories/")
    suspend fun getCategories(): Response<PaginatedDto<CategoryDto>>

    @GET("categories/{id}/")
    suspend fun getCategory(@Path("id") id: Int): Response<CategoryDto>

    @POST("categories/")
    suspend fun createCategory(@Body body: CategoryRequestDto): Response<CategoryDto>

    @PATCH("categories/{id}/")
    suspend fun updateCategory(
        @Path("id") id: Int,
        @Body body: CategoryRequestDto,
    ): Response<CategoryDto>

    @DELETE("categories/{id}/")
    suspend fun deleteCategory(@Path("id") id: Int): Response<Unit>

    @GET("categories/stats/")
    suspend fun getStats(): Response<CategoryStatsDto>
}