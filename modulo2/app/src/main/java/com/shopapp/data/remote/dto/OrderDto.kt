// data/remote/dto/OrderDto.kt
package com.shopapp.data.remote.dto

import com.google.gson.annotations.SerializedName
import com.shopapp.domain.model.Order
import com.shopapp.domain.model.OrderItem
import com.shopapp.domain.model.OrderStatus

data class ProductInItemDto(
    val id:    Int,
    val name:  String,
    val price: String,
    val stock: Int,
    @SerializedName("is_active") val isActive: Boolean,
)

data class OrderItemDto(
    val id:       Int,
    val product:  ProductInItemDto,
    val quantity: Int,
    @SerializedName("unit_price") val unitPrice: String,
    val subtotal: Double,
)

data class OrderDto(
    val id:       Int,
    val username: String,
    val status:   String,
    val total:    String,
    @SerializedName("num_items")  val numItems:  Int,
    val items:    List<OrderItemDto>,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("updated_at") val updatedAt: String,
)

data class AddItemRequestDto(
    @SerializedName("product_id") val productId: Int,
    val quantity: Int,
)

data class UpdateStatusRequestDto(
    val status: String,
)

data class OrderStatsDto(
    @SerializedName("total_orders")   val totalOrders:   Int,
    @SerializedName("total_revenue")  val totalRevenue:  Double,
    @SerializedName("by_status")      val byStatus:      Map<String, Int>,
)

// ── Mappers ───────────────────────────────────────────────────

fun OrderItemDto.toDomain() = OrderItem(
    id          = id,
    productId   = product.id,
    productName = product.name,
    quantity    = quantity,
    unitPrice   = unitPrice.toDoubleOrNull() ?: 0.0,
    subtotal    = subtotal,
)

fun OrderDto.toDomain() = Order(
    id        = id,
    username  = username,
    status    = OrderStatus.fromValue(status),
    total     = total.toDoubleOrNull() ?: 0.0,
    numItems  = numItems,
    items     = items.map { it.toDomain() },
    createdAt = createdAt,
    updatedAt = updatedAt,
)