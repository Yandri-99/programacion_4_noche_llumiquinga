package com.transportapp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.transportapp.domain.model.TripStatus
import com.transportapp.theme.*

fun tripStatusColor(status: TripStatus): Color = when (status) {
    TripStatus.PENDIENTE  -> Color(0xFFF59E0B)
    TripStatus.EN_CURSO   -> Color(0xFF3B82F6)
    TripStatus.COMPLETADO -> Color(0xFF22C55E)
    TripStatus.CANCELADO  -> Color(0xFFEF4444)
}

@Composable
fun StatusBadge(status: TripStatus, modifier: Modifier = Modifier) {
    val color = tripStatusColor(status)
    Row(
        modifier          = modifier
            .background(color.copy(alpha = 0.12f), RoundedCornerShape(999.dp))
            .padding(horizontal = 10.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        Box(
            modifier = Modifier
                .size(6.dp)
                .background(color, RoundedCornerShape(50)),
        )
        Text(
            text       = status.label,
            color      = color,
            fontSize   = 11.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 0.3.sp,
        )
    }
}
