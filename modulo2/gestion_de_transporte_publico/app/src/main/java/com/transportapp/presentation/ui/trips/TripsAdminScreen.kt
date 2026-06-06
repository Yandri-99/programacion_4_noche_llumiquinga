package com.transportapp.presentation.ui.trips

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
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
import com.transportapp.presentation.viewmodel.TripsAdminViewModel
import com.transportapp.theme.*

private val STATUS_FILTERS = listOf(
    "" to "Todos",
    TripStatus.PENDIENTE.value  to "Pendientes",
    TripStatus.EN_CURSO.value   to "En curso",
    TripStatus.COMPLETADO.value to "Completados",
    TripStatus.CANCELADO.value  to "Cancelados",
)

@Composable
fun TripsAdminScreen(
    onTripDetail: (Int) -> Unit,
    viewModel:    TripsAdminViewModel = hiltViewModel(),
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
    LaunchedEffect(shouldLoadMore) { if (shouldLoadMore) viewModel.loadMore() }

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
                            "Viajes",
                            style      = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            color      = TextPrimary,
                        )
                        Text(
                            "${state.total} viajes",
                            style = MaterialTheme.typography.bodySmall,
                            color = TextSecondary,
                        )
                    }
                    IconButton(onClick = viewModel::refresh) {
                        Icon(Icons.Default.Refresh, null, tint = TextSecondary)
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
                        Text("\uD83D\uDE8D", fontSize = 52.sp)
                        Spacer(Modifier.height(12.dp))
                        Text(
                            text       = "Sin viajes",
                            style      = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color      = TextPrimary,
                        )
                        Text(
                            text  = if (state.statusFilter.isBlank()) "A\u00FAn no hay viajes"
                            else "Sin viajes con este estado",
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
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                ) {
                    items(state.trips, key = { it.id }) { trip ->
                        TripAdminCard(
                            trip     = trip,
                            onStatus = { newStatus -> viewModel.changeStatus(trip.id, newStatus) },
                            onClick  = { onTripDetail(trip.id) },
                        )
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
private fun TripAdminCard(
    trip:    Trip,
    onStatus: (TripStatus) -> Unit,
    onClick:  () -> Unit,
) {
    val dateFmt   = java.text.SimpleDateFormat("dd MMM yyyy", java.util.Locale("es"))
    val inputFmt  = java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'", java.util.Locale.getDefault())
    val dateStr   = runCatching { dateFmt.format(inputFmt.parse(trip.createdAt)!!) }
        .getOrDefault(trip.createdAt.take(10))

    Surface(
        onClick = onClick,
        shape   = MaterialTheme.shapes.large,
        color   = Surface,
        modifier = Modifier.fillMaxWidth(),
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier              = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment     = Alignment.Top,
            ) {
                Column {
                    Text(
                        text       = "Viaje #${trip.id}",
                        style      = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color      = TextPrimary,
                    )
                    Text(
                        text  = "${trip.origin} \u2192 ${trip.destination} \u00B7 $dateStr",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextSecondary,
                    )
                }
                TripStatusDropdown(
                    current  = trip.estado,
                    onChange = onStatus,
                )
            }

            Spacer(Modifier.height(10.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                modifier              = Modifier.fillMaxWidth(),
            ) {
                Surface(color = Surface2, shape = MaterialTheme.shapes.extraSmall) {
                    Text(
                        text     = "\uD83D\uDC64 ${trip.driverName ?: "N/A"}",
                        style    = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                        color    = TextSecondary,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                        maxLines = 1,
                    )
                }
                Surface(color = Surface2, shape = MaterialTheme.shapes.extraSmall) {
                    Text(
                        text     = "\uD83D\uDE8C ${trip.vehiclePlaca ?: "N/A"}",
                        style    = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                        color    = TextSecondary,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                        maxLines = 1,
                    )
                }
            }

            Spacer(Modifier.height(10.dp))
            HorizontalDivider(color = BorderLight, thickness = 0.5.dp)
            Spacer(Modifier.height(8.dp))

            Row(
                modifier              = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment     = Alignment.CenterVertically,
            ) {
                Text(
                    text  = "${trip.pasajeros} pasajero${if (trip.pasajeros != 1) "s" else ""}",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextSecondary,
                )
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text       = "$${"%.2f".format(trip.total)}",
                        style      = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.ExtraBold,
                        color      = Accent,
                    )
                    Icon(
                        Icons.Default.ChevronRight,
                        contentDescription = "Ver detalle",
                        tint     = TextFaint,
                        modifier = Modifier.size(18.dp),
                    )
                }
            }
        }
    }
}
