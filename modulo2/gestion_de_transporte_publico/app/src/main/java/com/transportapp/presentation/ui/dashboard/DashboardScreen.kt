package com.transportapp.presentation.ui.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.transportapp.domain.model.TripStatus
import com.transportapp.presentation.components.LoadingScreen
import com.transportapp.presentation.viewmodel.DashboardUiState
import com.transportapp.presentation.viewmodel.DashboardViewModel
import com.transportapp.theme.*
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun tripStatusColor(status: TripStatus) = when (status) {
    TripStatus.PENDIENTE  -> StatusPending
    TripStatus.EN_CURSO   -> StatusEnCurso
    TripStatus.COMPLETADO -> StatusCompleted
    TripStatus.CANCELADO  -> StatusCancelled
}

@Composable
fun DashboardScreen(
    onNavigate: (String) -> Unit,
    viewModel:  DashboardViewModel = hiltViewModel(),
) {
    val state       by viewModel.state.collectAsState()
    val lastUpdated by viewModel.lastUpdated.collectAsState()

    when (val s = state) {
        is DashboardUiState.Loading ->
            LoadingScreen("Cargando dashboard...")
        is DashboardUiState.Error   -> {
            Box(Modifier.fillMaxSize(), Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("\u26A0\uFE0F ${s.message}", color = Error)
                    Spacer(Modifier.height(12.dp))
                    Button(onClick = viewModel::load,
                        colors = ButtonDefaults.buttonColors(containerColor = Accent)) {
                        Text("Reintentar", color = AccentOnDark)
                    }
                }
            }
        }
        is DashboardUiState.Success ->
            DashboardContent(
                stats       = s.stats,
                lastUpdated = lastUpdated,
                onNavigate  = onNavigate,
                onRefresh   = viewModel::load,
            )
    }
}

@Composable
private fun DashboardContent(
    stats:       com.transportapp.presentation.viewmodel.DashboardStats,
    lastUpdated: Long,
    onNavigate:  (String) -> Unit,
    onRefresh:   () -> Unit,
) {
    val timeFmt = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
    val timeStr = if (lastUpdated > 0) timeFmt.format(Date(lastUpdated)) else "\u2014"

    LazyColumn(
        modifier       = Modifier.fillMaxSize().background(Background),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        item {
            Row(
                modifier              = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment     = Alignment.CenterVertically,
            ) {
                Column {
                    Text(
                        text       = "Dashboard",
                        style      = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color      = TextPrimary,
                    )
                    Text(
                        text  = "Actualizado: $timeStr",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextFaint,
                    )
                }
                IconButton(onClick = onRefresh) {
                    Icon(Icons.Default.Refresh, contentDescription = "Actualizar", tint = Accent)
                }
            }
        }

        item {
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                KpiCard(
                    title    = "Viajes activos",
                    value    = (stats.viajesPorEstado["en_curso"] ?: 0).toString(),
                    subtitle = if (stats.viajesPendientes > 0)
                        "${stats.viajesPendientes} pendientes" else null,
                    icon     = Icons.Default.Route,
                    color    = Accent,
                    hasAlert = stats.viajesPendientes > 0,
                    onClick  = { onNavigate("admin/trips") },
                    modifier = Modifier.weight(1f),
                )
                KpiCard(
                    title   = "Veh\u00edculos",
                    value   = stats.totalVehiculos.toString(),
                    subtitle = "${stats.vehiculosActivos} activos",
                    icon    = Icons.Default.DirectionsBus,
                    color   = Info,
                    onClick = { onNavigate("admin/vehicles") },
                    modifier = Modifier.weight(1f),
                )
            }
        }

        item {
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                KpiCard(
                    title    = "Rutas activas",
                    value    = stats.totalRutas.toString(),
                    subtitle = "${stats.totalRutas} total",
                    icon     = Icons.Default.Map,
                    color    = Success,
                    onClick  = { onNavigate("admin/routes") },
                    modifier = Modifier.weight(1f),
                )
                KpiCard(
                    title    = "Conductores",
                    value    = stats.totalConductores.toString(),
                    subtitle = "${stats.conductoresDisponibles} disponibles",
                    icon     = Icons.Default.People,
                    color    = Warning,
                    hasAlert = stats.conductoresDisponibles < stats.totalConductores,
                    onClick  = { onNavigate("admin/drivers") },
                    modifier = Modifier.weight(1f),
                )
            }
        }

        if (stats.viajesPorEstado.isNotEmpty()) {
            item {
                Surface(
                    color    = Surface,
                    shape    = MaterialTheme.shapes.large,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier              = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment     = Alignment.CenterVertically,
                        ) {
                            Text(
                                text       = "Viajes por estado",
                                style      = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color      = TextPrimary,
                            )
                            TextButton(onClick = { onNavigate("admin/trips") }) {
                                Text("Ver todos", color = Accent,
                                    style = MaterialTheme.typography.bodySmall)
                            }
                        }
                        Spacer(Modifier.height(16.dp))

                        val total = stats.viajesPorEstado.values.sum().coerceAtLeast(1)
                        stats.viajesPorEstado.entries.forEach { (statusValue, count) ->
                            val status = TripStatus.fromValue(statusValue)
                            val color  = tripStatusColor(status)
                            val pct    = (count.toFloat() / total).coerceIn(0.02f, 1f)

                            Column(modifier = Modifier.padding(bottom = 10.dp)) {
                                Row(
                                    modifier              = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                ) {
                                    Text(
                                        text  = status.label,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = TextSecondary,
                                    )
                                    Text(
                                        text       = count.toString(),
                                        style      = MaterialTheme.typography.bodySmall,
                                        fontWeight = FontWeight.Bold,
                                        color      = color,
                                    )
                                }
                                Spacer(Modifier.height(4.dp))
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(7.dp)
                                        .background(Surface2, MaterialTheme.shapes.extraSmall),
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth(pct)
                                            .fillMaxHeight()
                                            .background(color, MaterialTheme.shapes.extraSmall),
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        item {
            Surface(
                color    = Surface,
                shape    = MaterialTheme.shapes.large,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text       = "\u26A1 Acciones r\u00E1pidas",
                        style      = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color      = TextPrimary,
                        modifier   = Modifier.padding(bottom = 12.dp),
                    )
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(listOf(
                            Triple("+ Ruta",     Info,    "admin/routes"),
                            Triple("+ Veh\u00EDculo", Accent,  "admin/vehicles"),
                            Triple("Ver viajes", Success, "admin/trips"),
                            Triple("Conductores", Warning, "admin/drivers"),
                        )) { (label, color, route) ->
                            Surface(
                                onClick  = { onNavigate(route) },
                                color    = color.copy(alpha = 0.1f),
                                shape    = MaterialTheme.shapes.medium,
                            ) {
                                Text(
                                    text       = label,
                                    color      = color,
                                    fontWeight = FontWeight.Bold,
                                    style      = MaterialTheme.typography.bodySmall,
                                    modifier   = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
