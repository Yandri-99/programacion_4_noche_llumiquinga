package com.shopapp.domain.model

data class NotificationResult(
    val detail: String,
    val sent:   Int,
    val failed: Int,
)
