package com.transportapp.presentation.ui.trips

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
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
import com.transportapp.presentation.components.StatusBadge
import com.transportapp.presentation.components.LoadingScreen
import com.transportapp.presentation.components.ErrorScreen
import com.transportapp.presentation.viewmodel.TripsViewModel
import com.transportapp.theme.*
import java.text.SimpleDateFormat
import java.util.Locale

private val STATUS_FILTERS = listOf(
    "" to "Todos",
    TripStatus.PENDIENTE.value   to "Pendientes",
    TripStatus.EN_CURSO.value    to "En curso",
    TripStatus.COMPLETADO.value  to "Completados",
    TripStatus.CANCELADO.value   to "Cancelados",
)

@Composable
fun TripsScreen(
    onTripClick: (Int) -> Unit,
    viewModel:   TripsViewModel = hiltViewModel(),
) {
    val state     by viewModel.state.collectAsState()
    val listState  = rememberLazyListState()

    val shouldLoadMore by remember {
        derivedStateOf {
            val lastVisible = listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
            val total       = listState.layoutInfo.totalItemsCount
            lastVisible >= total - 2 && !state.isLoadingMore && state.hasMore
        }
    }
    LaunchedEffect(shouldLoadMore) {
        if (shouldLoadMore) viewModel.loadMore()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background),
    ) {
        Surface(color = Surface, tonalElevation = 0.dp) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier              = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment     = Alignment.CenterVertically,
                ) {
                    Column {
                        Text(
                            text       = "Mis viajes",
                            style      = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            color      = TextPrimary,
                        )
                        Text(
                            text  = if (state.isLoading) "..." else "${state.total} viajes",
                            style = MaterialTheme.typography.bodySmall,
                            color = TextSecondary,
                        )
                    }
                    IconButton(onClick = viewModel::refresh) {
                        Icon(Icons.Default.Refresh, contentDescription = "Actualizar", tint = TextSecondary)
                    }
                }

                Spacer(Modifier.height(12.dp))

                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(STATUS_FILTERS) { (value, label) ->
                        FilterChip(
                            selected = state.statusFilter == value,
                            onClick  = { viewModel.setStatusFilter(value) },
                            label    = { Text(label, style = MaterialTheme.typography.labelSmall) },
                            colors   = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = Accent,
                                selectedLabelColor     = AccentOnDark,
                                containerColor         = Surface2,
                                labelColor             = TextSecondary,
                            ),
                        )
                    }
                }
            }
        }

        when {
            state.isLoading && state.trips.isEmpty() ->
                LoadingScreen("Cargando viajes...")

            state.error != null && state.trips.isEmpty() ->
                ErrorScreen(state.error!!, onRetry = viewModel::refresh)

            state.trips.isEmpty() -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("🚌", fontSize = 52.sp)
                        Spacer(Modifier.height(12.dp))
                        Text(
                            text       = if (state.statusFilter.isBlank()) "Sin viajes"
                            else "Sin viajes con este estado",
                            style      = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color      = TextPrimary,
                        )
                        Text(
                            text  = "Tus viajes aparecerán aquí",
                            style = MaterialTheme.typography.bodySmall,
                            color = TextSecondary,
                        )
                    }
                }
            }

            else -> {
                LazyColumn(
                    state          = listState,
                    modifier       = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    items(state.trips, key = { it.id }) { trip ->
                        TripCard(trip = trip, onClick = { onTripClick(trip.id) })
                    }

                    if (state.isLoadingMore) {
                        item {
                            Box(
                                modifier         = Modifier.fillMaxWidth().padding(16.dp),
                                contentAlignment = Alignment.Center,
                            ) {
                                CircularProgressIndicator(
                                    color       = Accent,
                                    modifier    = Modifier.size(28.dp),
                                    strokeWidth = 2.dp,
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
fun TripCard(trip: Trip, onClick: () -> Unit) {
    val inputFmt  = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'", Locale.getDefault())
    val outputFmt = SimpleDateFormat("dd MMM yyyy", Locale("es"))
    val dateStr   = runCatching {
        outputFmt.format(inputFmt.parse(trip.createdAt)!!)
    }.getOrDefault(trip.createdAt.take(10))

    Surface(
        onClick        = onClick,
        shape          = MaterialTheme.shapes.large,
        color          = Surface,
        tonalElevation = 0.dp,
        modifier       = Modifier.fillMaxWidth(),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier              = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment     = Alignment.Top,
            ) {
                Column {
                    Text(
                        text       = "Viaje #${trip.id}",
                        style      = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color      = TextPrimary,
                    )
                    Text(
                        text  = dateStr,
                        style = MaterialTheme.typography.bodySmall,
                        color = TextSecondary,
                    )
                }
                StatusBadge(status = trip.estado)
            }

            Spacer(Modifier.height(12.dp))

            Row(
                modifier              = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Surface(
                    color  = Surface2,
                    shape  = MaterialTheme.shapes.extraSmall,
                    modifier = Modifier.weight(1f),
                ) {
                    Column(modifier = Modifier.padding(8.dp)) {
                        Text("Origen", style = MaterialTheme.typography.bodySmall.copy(fontSize = 10.sp), color = TextFaint)
                        Text(trip.origin, style = MaterialTheme.typography.bodySmall, color = TextPrimary, fontWeight = FontWeight.SemiBold)
                    }
                }
                Surface(
                    color  = Surface2,
                    shape  = MaterialTheme.shapes.extraSmall,
                    modifier = Modifier.weight(1f),
                ) {
                    Column(modifier = Modifier.padding(8.dp)) {
                        Text("Destino", style = MaterialTheme.typography.bodySmall.copy(fontSize = 10.sp), color = TextFaint)
                        Text(trip.destination, style = MaterialTheme.typography.bodySmall, color = TextPrimary, fontWeight = FontWeight.SemiBold)
                    }
                }
            }

            Spacer(Modifier.height(12.dp))
            HorizontalDivider(color = BorderLight, thickness = 0.5.dp)
            Spacer(Modifier.height(10.dp))

            Row(
                modifier              = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment     = Alignment.CenterVertically,
            ) {
                Column {
                    if (trip.driverName != null) {
                        Text(
                            text  = "Conductor: ${trip.driverName}",
                            style = MaterialTheme.typography.bodySmall,
                            color = TextSecondary,
                        )
                    }
                    if (trip.vehiclePlaca != null) {
                        Text(
                            text  = "${trip.vehiclePlaca} · ${trip.pasajeros} pasajeros",
                            style = MaterialTheme.typography.bodySmall,
                            color = TextSecondary,
                        )
                    }
                }
                Text(
                    text       = "$${"%.2f".format(trip.total)}",
                    style      = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.ExtraBold,
                    color      = Accent,
                )
            }
        }
    }
}
