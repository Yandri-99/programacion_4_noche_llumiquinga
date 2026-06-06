package com.transportapp.presentation.ui.cart

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.transportapp.presentation.viewmodel.CartViewModel
import com.transportapp.presentation.viewmodel.ReservationItem
import com.transportapp.presentation.viewmodel.ReservationState
import com.transportapp.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReservationBottomSheet(
    cartViewModel:   CartViewModel,
    isAuthenticated:      Boolean,
    onDismiss:            () -> Unit,
    onLoginRequired:      () -> Unit,
    onTripSuccess: (Int) -> Unit,
) {
    val items            by cartViewModel.items.collectAsState()
    val totalPrecio      by cartViewModel.totalPrecio.collectAsState()
    val totalPasajeros   by cartViewModel.totalPasajeros.collectAsState()
    val reservationState by cartViewModel.reservationState.collectAsState()

    LaunchedEffect(reservationState) {
        if (reservationState is ReservationState.Success) {
            onTripSuccess((reservationState as ReservationState.Success).tripId)
            cartViewModel.resetReservation()
        }
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor   = Surface,
        dragHandle       = {
            Box(
                modifier = Modifier
                    .padding(vertical = 12.dp)
                    .size(40.dp, 4.dp)
                    .background(Border, RoundedCornerShape(2.dp)),
            )
        },
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(bottom = 16.dp),
        ) {
            Row(
                modifier              = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment     = Alignment.CenterVertically,
            ) {
                Column {
                    Text(
                        text       = "Mis reservas",
                        style      = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color      = TextPrimary,
                    )
                    if (items.isNotEmpty()) {
                        Text(
                            text  = "$totalPasajeros pasajero${if (totalPasajeros != 1) "s" else ""}",
                            style = MaterialTheme.typography.bodySmall,
                            color = TextSecondary,
                        )
                    }
                }
                if (items.isNotEmpty()) {
                    IconButton(onClick = { cartViewModel.clearCart() }) {
                        Icon(Icons.Default.DeleteOutline, contentDescription = "Vaciar", tint = Error)
                    }
                }
            }

            Spacer(Modifier.height(16.dp))
            HorizontalDivider(color = Border, thickness = 0.5.dp)

            if (items.isEmpty() && reservationState !is ReservationState.Success) {
                Column(
                    modifier            = Modifier
                        .fillMaxWidth()
                        .padding(48.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text("🚌", fontSize = 52.sp)
                    Spacer(Modifier.height(12.dp))
                    Text(
                        "Sin reservas",
                        style      = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color      = TextPrimary,
                    )
                    Text(
                        "Reserva vehículos desde el catálogo",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextSecondary,
                    )
                }
            }

            if (items.isNotEmpty()) {
                LazyColumn(
                    modifier          = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 320.dp),
                    contentPadding    = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    items(items, key = { it.vehicle.id }) { item ->
                        ReservationItemRow(
                            item          = item,
                            onIncrease    = { cartViewModel.updatePasajeros(item.vehicle.id, item.cantidadPasajeros + 1) },
                            onDecrease    = { cartViewModel.updatePasajeros(item.vehicle.id, item.cantidadPasajeros - 1) },
                            onRemove      = { cartViewModel.removeItem(item.vehicle.id) },
                        )
                    }
                }

                HorizontalDivider(color = Border, thickness = 0.5.dp)

                Column(modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp)) {
                    TotalRow("Total pasajeros", "$totalPasajeros", false)
                    Spacer(Modifier.height(8.dp))
                    HorizontalDivider(color = Border, thickness = 0.5.dp)
                    Spacer(Modifier.height(8.dp))
                    TotalRow("Total", "$${"%.2f".format(totalPrecio)}", true)
                }

                if (reservationState is ReservationState.Error) {
                    Surface(
                        color    = Error.copy(alpha = 0.1f),
                        shape    = MaterialTheme.shapes.small,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp),
                    ) {
                        Text(
                            text     = (reservationState as ReservationState.Error).message,
                            color    = Error,
                            style    = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(12.dp),
                        )
                    }
                    Spacer(Modifier.height(8.dp))
                }

                if (!isAuthenticated) {
                    Surface(
                        color    = Accent.copy(alpha = 0.08f),
                        shape    = MaterialTheme.shapes.small,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp),
                    ) {
                        Text(
                            text     = "💡 Inicia sesión para confirmar la reserva",
                            color    = Accent,
                            style    = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(12.dp),
                        )
                    }
                    Spacer(Modifier.height(12.dp))
                }

                val isLoading = reservationState is ReservationState.Loading
                Button(
                    onClick = {
                        if (!isAuthenticated) onLoginRequired()
                        else cartViewModel.reservar()
                    },
                    enabled  = !isLoading,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .padding(horizontal = 24.dp),
                    colors   = ButtonDefaults.buttonColors(
                        containerColor         = Accent,
                        contentColor           = AccentOnDark,
                        disabledContainerColor = Accent.copy(alpha = 0.5f),
                    ),
                    shape    = MaterialTheme.shapes.medium,
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(
                            color       = AccentOnDark,
                            modifier    = Modifier.size(18.dp),
                            strokeWidth = 2.dp,
                        )
                        Spacer(Modifier.width(8.dp))
                        Text("Procesando...", fontWeight = FontWeight.Bold)
                    } else if (!isAuthenticated) {
                        Text("Iniciar sesión para reservar", fontWeight = FontWeight.Bold)
                    } else {
                        Text(
                            "Confirmar — $${"%.2f".format(totalPrecio)}",
                            fontWeight = FontWeight.Bold,
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ReservationItemRow(
    item:       ReservationItem,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit,
    onRemove:   () -> Unit,
) {
    Surface(
        color  = Surface2,
        shape  = MaterialTheme.shapes.medium,
        modifier = Modifier.fillMaxWidth(),
    ) {
        Row(
            modifier          = Modifier.padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier         = Modifier
                    .size(58.dp)
                    .background(Surface, RoundedCornerShape(10.dp)),
                contentAlignment = Alignment.Center,
            ) {
                Text("🚌", fontSize = 24.sp)
            }

            Spacer(Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text     = "${item.vehicle.name} · ${item.vehicle.placa}",
                    style    = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold,
                    color    = TextPrimary,
                    maxLines = 2,
                )
                Text(
                    text  = "$${"%.2f".format(item.vehicle.precioPasaje)} / pasajero",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextSecondary,
                )
            }

            Spacer(Modifier.width(8.dp))

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = onDecrease, modifier = Modifier.size(28.dp)) {
                        Icon(Icons.Default.Remove, null, tint = TextSecondary, modifier = Modifier.size(14.dp))
                    }
                    Text(
                        text       = item.cantidadPasajeros.toString(),
                        fontWeight = FontWeight.Bold,
                        color      = TextPrimary,
                        modifier   = Modifier.padding(horizontal = 8.dp),
                    )
                    IconButton(
                        onClick  = onIncrease,
                        enabled  = item.cantidadPasajeros < item.vehicle.capacidad,
                        modifier = Modifier.size(28.dp),
                    ) {
                        Icon(
                            Icons.Default.Add, null,
                            tint     = if (item.cantidadPasajeros < item.vehicle.capacidad) TextSecondary else TextFaint,
                            modifier = Modifier.size(14.dp),
                        )
                    }
                }
                Text(
                    text       = "$${"%.2f".format(item.vehicle.precioPasaje * item.cantidadPasajeros)}",
                    style      = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Bold,
                    color      = Accent,
                )
            }

            IconButton(onClick = onRemove, modifier = Modifier.size(32.dp)) {
                Icon(Icons.Default.Close, contentDescription = "Quitar", tint = TextFaint, modifier = Modifier.size(16.dp))
            }
        }
    }
}

@Composable
private fun TotalRow(label: String, value: String, isFinal: Boolean) {
    Row(
        modifier              = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment     = Alignment.CenterVertically,
    ) {
        Text(
            text       = label,
            style      = if (isFinal) MaterialTheme.typography.titleMedium else MaterialTheme.typography.bodyMedium,
            fontWeight = if (isFinal) FontWeight.Bold else FontWeight.Normal,
            color      = if (isFinal) TextPrimary else TextSecondary,
        )
        Text(
            text       = value,
            style      = if (isFinal) MaterialTheme.typography.titleMedium else MaterialTheme.typography.bodyMedium,
            fontWeight = if (isFinal) FontWeight.ExtraBold else FontWeight.SemiBold,
            color      = if (isFinal) Accent else TextPrimary,
        )
    }
}
