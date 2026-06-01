// data/remote/dto/ProductDto.kt
package com.shopapp.data.remote.dto

import com.google.gson.annotations.SerializedName
import com.shopapp.domain.model.Product
import com.shopapp.domain.model.ProductPayload

data class CategorySummaryDto(
    val id:   Int,
    val name: String,
)

data class ProductDto(
    val id:          Int,
    val name:        String,
    val description: String,
    val price:       String,          // Django devuelve Decimal como String
    @SerializedName("price_with_tax") val priceWithTax: Double,
    val stock:       Int,
    @SerializedName("in_stock")  val inStock:  Boolean,
    @SerializedName("is_active") val isActive: Boolean,
    val image:       String?,
    @SerializedName("image_url") val imageUrl: String?,
    val category:    CategorySummaryDto?,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("updated_at") val updatedAt: String,
)

data class ProductRequestDto(
    val name:        String,
    val description: String,
    val price:       Double,
    val stock:       Int,
    @SerializedName("is_active")   val isActive:   Boolean,
    @SerializedName("category_id") val categoryId: Int,
)

data class ProductStatsDto(
    @SerializedName("total_active")   val totalActive:   Int,
    @SerializedName("total_inactive") val totalInactive: Int,
    @SerializedName("avg_price")      val avgPrice:      Double?,
    @SerializedName("max_price")      val maxPrice:      Double?,
    @SerializedName("min_price")      val minPrice:      Double?,
    @SerializedName("total_stock")    val totalStock:    Int?,
    @SerializedName("out_of_stock")   val outOfStock:    Int,
)

data class RestockResponseDto(
    val id:          Int,
    val name:        String,
    @SerializedName("new_stock") val newStock: Int,
)

data class RestockRequestDto(
    val quantity: Int,
)

// ── Mappers ───────────────────────────────────────────────────

fun ProductDto.toDomain() = Product(
    id           = id,
    name         = name,
    description  = description,
    price        = price.toDoubleOrNull() ?: 0.0,
    priceWithTax = priceWithTax,
    stock        = stock,
    inStock      = inStock,
    isActive     = isActive,
    imageUrl     = imageUrl,
    categoryId   = category?.id,
    categoryName = category?.name,
    createdAt    = createdAt,
    updatedAt    = updatedAt,
)

fun ProductPayload.toRequest() = ProductRequestDto(
    name        = name,
    description = description,
    price       = price,
    stock       = stock,
    isActive    = isActive,
    categoryId  = categoryId,
)