package com.transportapp.presentation.ui.uipublic.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.transportapp.domain.model.Vehicle
import com.transportapp.presentation.viewmodel.CatalogViewModel
import com.transportapp.theme.*

@Composable
fun HomeScreen(
    onVehicleClick:  (Int) -> Unit,
    onCatalogClick:  () -> Unit,
    viewModel:       CatalogViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()

    LazyColumn(
        modifier            = Modifier
            .fillMaxSize()
            .background(Background),
        contentPadding      = PaddingValues(bottom = 24.dp),
    ) {
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = androidx.compose.ui.graphics.Brush.verticalGradient(
                            listOf(Surface2, Background),
                        ),
                    )
                    .padding(horizontal = 24.dp, vertical = 48.dp),
            ) {
                Column {
                    Text(
                        text       = "Viaja con",
                        fontSize   = 32.sp,
                        fontWeight = FontWeight.Normal,
                        color      = TextSecondary,
                    )
                    Text(
                        text       = "TransportApp",
                        fontSize   = 34.sp,
                        fontWeight = FontWeight.Bold,
                        color      = Accent,
                    )
                    Spacer(Modifier.height(16.dp))
                    Text(
                        text  = "Las mejores rutas y vehículos para tu transporte.",
                        color = TextSecondary,
                        style = MaterialTheme.typography.bodyMedium,
                    )
                    Spacer(Modifier.height(24.dp))
                    Button(
                        onClick = onCatalogClick,
                        colors  = ButtonDefaults.buttonColors(
                            containerColor = Accent,
                            contentColor   = AccentOnDark,
                        ),
                        shape = MaterialTheme.shapes.medium,
                    ) {
                        Text("Ver vehículos", fontWeight = FontWeight.Bold)
                        Spacer(Modifier.width(8.dp))
                        Icon(Icons.Default.ArrowForward, contentDescription = null, modifier = Modifier.size(16.dp))
                    }
                }
            }
        }

        item {
            SectionHeader(title = "Estadísticas", onSeeAll = null)
        }
        item {
            Row(
                modifier              = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                QuickStatCard(title = "Rutas", value = "${state.total}", color = Accent, modifier = Modifier.weight(1f))
                QuickStatCard(title = "Vehículos", value = "${state.vehicles.size}", color = Info, modifier = Modifier.weight(1f))
                QuickStatCard(title = "En servicio", value = "${state.vehicles.count { it.estado == "activo" }}", color = Success, modifier = Modifier.weight(1f))
            }
        }

        item {
            Spacer(Modifier.height(24.dp))
        }

        item {
            SectionHeader(title = "Vehículos disponibles", onSeeAll = onCatalogClick)
        }

        if (state.isLoading) {
            item {
                Box(Modifier.fillMaxWidth().height(200.dp), Alignment.Center) {
                    CircularProgressIndicator(color = Accent)
                }
            }
        } else {
            val chunked = state.vehicles.take(4).chunked(2)
            items(chunked) { row ->
                Row(
                    modifier              = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    row.forEach { vehicle ->
                        VehicleCard(
                            vehicle  = vehicle,
                            onClick  = { onVehicleClick(vehicle.id) },
                            modifier = Modifier.weight(1f),
                        )
                    }
                    if (row.size == 1) Spacer(Modifier.weight(1f))
                }
                Spacer(Modifier.height(12.dp))
            }
        }
    }
}

@Composable
private fun SectionHeader(title: String, onSeeAll: (() -> Unit)?) {
    Row(
        modifier              = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment     = Alignment.CenterVertically,
    ) {
        Text(
            text       = title,
            style      = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color      = TextPrimary,
        )
        if (onSeeAll != null) {
            TextButton(onClick = onSeeAll) {
                Text("Ver todos", color = Accent, style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}

@Composable
private fun QuickStatCard(title: String, value: String, color: androidx.compose.ui.graphics.Color, modifier: Modifier = Modifier) {
    Surface(
        shape          = MaterialTheme.shapes.medium,
        color          = Surface2,
        tonalElevation = 0.dp,
        modifier       = modifier,
    ) {
        Column(
            modifier            = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text       = value,
                fontSize   = 24.sp,
                fontWeight = FontWeight.Bold,
                color      = color,
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text  = title,
                style = MaterialTheme.typography.bodySmall,
                color = TextSecondary,
            )
        }
    }
}

@Composable
fun VehicleCard(
    vehicle:  Vehicle,
    onClick:  () -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(
        onClick        = onClick,
        shape          = MaterialTheme.shapes.large,
        color          = Surface,
        tonalElevation = 0.dp,
        modifier       = modifier,
    ) {
        Column {
            Box(
                modifier          = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .background(Surface2),
                contentAlignment  = Alignment.Center,
            ) {
                Text("🚌", fontSize = 42.sp)
            }

            Column(modifier = Modifier.padding(12.dp)) {
                Text(
                    text     = vehicle.tipo,
                    style    = MaterialTheme.typography.labelSmall,
                    color    = Accent,
                    maxLines = 1,
                )
                Spacer(Modifier.height(2.dp))
                Text(
                    text       = "${vehicle.name} · ${vehicle.placa}",
                    style      = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold,
                    color      = TextPrimary,
                    maxLines   = 2,
                )
                Spacer(Modifier.height(6.dp))
                Text(
                    text       = "$${"%.2f".format(vehicle.precioPasaje)}",
                    style      = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color      = Accent,
                )
                Text(
                    text  = "Capacidad: ${vehicle.capacidad} pasajeros",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextSecondary,
                )
            }
        }
    }
}
