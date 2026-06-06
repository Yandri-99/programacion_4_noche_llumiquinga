package com.transportapp.presentation.ui.trips

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import com.transportapp.domain.model.Trip
import com.transportapp.domain.model.TripStatus
import com.transportapp.presentation.components.LoadingScreen
import com.transportapp.presentation.components.ErrorScreen
import com.transportapp.presentation.viewmodel.TripDetailUiState
import com.transportapp.presentation.viewmodel.TripDetailViewModel
import com.transportapp.theme.*
import java.text.SimpleDateFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TripAdminDetailScreen(
    tripId:      Int,
    onBack:      () -> Unit,
    onStatusChange: (Int, TripStatus) -> Unit,
    viewModel:   TripDetailViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()
    LaunchedEffect(tripId) { viewModel.load(tripId) }

    when (val s = state) {
        is TripDetailUiState.Loading ->
            LoadingScreen("Cargando viaje #$tripId...")
        is TripDetailUiState.Error   ->
            ErrorScreen(s.message, onRetry = { viewModel.load(tripId) })
        is TripDetailUiState.Success ->
            AdminTripDetailContent(
                trip          = s.trip,
                onBack        = onBack,
                onStatusChange = { newStatus ->
                    onStatusChange(tripId, newStatus)
                    viewModel.load(tripId)
                },
            )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AdminTripDetailContent(
    trip:          Trip,
    onBack:        () -> Unit,
    onStatusChange: (TripStatus) -> Unit,
) {
    val inputFmt  = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'", Locale.getDefault())
    val outputFmt = SimpleDateFormat("dd MMM yyyy \u00B7 HH:mm", Locale("es"))
    val dateStr   = runCatching { outputFmt.format(inputFmt.parse(trip.createdAt)!!) }
        .getOrDefault(trip.createdAt.take(16))
    val updatedStr = runCatching { outputFmt.format(inputFmt.parse(trip.updatedAt ?: trip.createdAt)!!) }
        .getOrDefault(trip.updatedAt?.take(16) ?: trip.createdAt.take(16))

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            "Viaje #${trip.id}",
                            style      = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color      = TextPrimary,
                        )
                        Text(
                            "${trip.origin} \u2192 ${trip.destination}",
                            style = MaterialTheme.typography.bodySmall,
                            color = TextSecondary,
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, null, tint = TextPrimary)
                    }
                },
                actions = {
                    TripStatusDropdown(
                        current  = trip.estado,
                        onChange = onStatusChange,
                        modifier = Modifier.padding(end = 12.dp),
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Surface),
            )
        },
        containerColor = Background,
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp),
        ) {
            Surface(color = Surface, shape = MaterialTheme.shapes.large, modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    SectionLabel("Informaci\u00F3n del viaje")
                    Spacer(Modifier.height(12.dp))
                    InfoGrid(listOf(
                        "Ruta"         to "${trip.origin} \u2192 ${trip.destination}",
                        "Fecha"        to dateStr,
                        "Actualizado"  to updatedStr,
                        "Pasajeros"    to "${trip.pasajeros}",
                    ))
                }
            }

            Surface(color = Surface, shape = MaterialTheme.shapes.large, modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    SectionLabel("Conductor y veh\u00EDculo")
                    Spacer(Modifier.height(12.dp))
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Row(
                            modifier              = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment     = Alignment.CenterVertically,
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                                Box(
                                    modifier         = Modifier
                                        .size(44.dp)
                                        .background(Surface2, MaterialTheme.shapes.small),
                                    contentAlignment = Alignment.Center,
                                ) {
                                    Text("\uD83D\uDC64", fontSize = 20.sp)
                                }
                                Spacer(Modifier.width(12.dp))
                                Column {
                                    Text(
                                        text       = trip.driverName ?: "Sin asignar",
                                        style      = MaterialTheme.typography.bodyMedium,
                                        fontWeight = FontWeight.SemiBold,
                                        color      = TextPrimary,
                                    )
                                    Text(
                                        text  = "ID conductor: ${trip.driverId}",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = TextSecondary,
                                    )
                                }
                            }
                        }
                        HorizontalDivider(color = BorderLight, thickness = 0.5.dp)
                        Row(
                            modifier              = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment     = Alignment.CenterVertically,
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                                Box(
                                    modifier         = Modifier
                                        .size(44.dp)
                                        .background(Surface2, MaterialTheme.shapes.small),
                                    contentAlignment = Alignment.Center,
                                ) {
                                    Text("\uD83D\uDE8C", fontSize = 20.sp)
                                }
                                Spacer(Modifier.width(12.dp))
                                Column {
                                    Text(
                                        text       = trip.vehiclePlaca ?: "Sin asignar",
                                        style      = MaterialTheme.typography.bodyMedium,
                                        fontWeight = FontWeight.SemiBold,
                                        color      = TextPrimary,
                                    )
                                    Text(
                                        text  = "ID veh\u00EDculo: ${trip.vehicleId}",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = TextSecondary,
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Surface(color = Surface, shape = MaterialTheme.shapes.large, modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    SectionLabel("Resumen financiero")
                    Spacer(Modifier.height(12.dp))
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        FinancialRow("Total ingresos", trip.total, true)
                    }
                }
            }

            Surface(color = Surface, shape = MaterialTheme.shapes.large, modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    SectionLabel("Cambiar estado")
                    Spacer(Modifier.height(12.dp))
                    Row(
                        modifier              = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        TripStatus.entries
                            .filter { it != trip.estado }
                            .forEach { status ->
                                Surface(
                                    onClick   = { onStatusChange(status) },
                                    shape     = MaterialTheme.shapes.small,
                                    color     = tripStatusColor(status).copy(alpha = 0.1f),
                                    modifier  = Modifier.weight(1f),
                                ) {
                                    Text(
                                        text     = status.label,
                                        color    = tripStatusColor(status),
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(8.dp),
                                    )
                                }
                            }
                    }
                }
            }
        }
    }
}

@Composable
private fun SectionLabel(text: String) {
    Text(
        text          = text,
        style         = MaterialTheme.typography.labelSmall,
        color         = TextSecondary,
        letterSpacing = 0.8.sp,
    )
}

@Composable
private fun InfoGrid(items: List<Pair<String, String>>) {
    items.chunked(2).forEach { row ->
        Row(
            modifier              = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            row.forEach { (label, value) ->
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text  = label,
                        style = MaterialTheme.typography.bodySmall,
                        color = TextSecondary,
                    )
                    Text(
                        text       = value,
                        style      = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold,
                        color      = TextPrimary,
                    )
                    Spacer(Modifier.height(10.dp))
                }
            }
            if (row.size == 1) Spacer(Modifier.weight(1f))
        }
    }
}

@Composable
private fun FinancialRow(label: String, value: Double, isFinal: Boolean = false) {
    Row(
        modifier              = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text       = label,
            style      = if (isFinal) MaterialTheme.typography.titleSmall
            else MaterialTheme.typography.bodyMedium,
            fontWeight = if (isFinal) FontWeight.Bold else FontWeight.Normal,
            color      = if (isFinal) TextPrimary else TextSecondary,
        )
        Text(
            text       = "$${"%.2f".format(value)}",
            style      = if (isFinal) MaterialTheme.typography.titleSmall
            else MaterialTheme.typography.bodyMedium,
            fontWeight = if (isFinal) FontWeight.ExtraBold else FontWeight.SemiBold,
            color      = if (isFinal) Accent else TextPrimary,
        )
    }
}
