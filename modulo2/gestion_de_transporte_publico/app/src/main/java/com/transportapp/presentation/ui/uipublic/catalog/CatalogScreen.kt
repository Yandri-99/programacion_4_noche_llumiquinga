package com.transportapp.presentation.ui.uipublic.catalog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.transportapp.presentation.ui.uipublic.home.VehicleCard
import com.transportapp.presentation.viewmodel.CatalogViewModel
import com.transportapp.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatalogScreen(
    onVehicleClick: (Int) -> Unit,
    viewModel: CatalogViewModel = hiltViewModel(),
) {
    val state    by viewModel.state.collectAsState()
    val gridState = rememberLazyGridState()

    val shouldLoadMore by remember {
        derivedStateOf {
            val lastVisible = gridState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
            val total       = gridState.layoutInfo.totalItemsCount
            lastVisible >= total - 3 && !state.isLoadingMore && state.hasMore
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
                    Text(
                        text       = "Vehículos",
                        style      = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color      = TextPrimary,
                    )
                    Text(
                        text  = "${state.total} unidades",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextSecondary,
                    )
                }

                Spacer(Modifier.height(12.dp))

                OutlinedTextField(
                    value         = state.search,
                    onValueChange = viewModel::setSearch,
                    placeholder   = { Text("Buscar vehículos...", color = TextFaint) },
                    leadingIcon   = { Icon(Icons.Default.Search, null, tint = TextSecondary) },
                    singleLine    = true,
                    modifier      = Modifier.fillMaxWidth(),
                    shape         = MaterialTheme.shapes.medium,
                    colors        = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor   = Accent,
                        unfocusedBorderColor = Border,
                        cursorColor          = Accent,
                    ),
                )

                Spacer(Modifier.height(12.dp))

                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(listOf(
                        "" to "Relevancia",
                        "precioPasaje"  to "Precio ↑",
                        "-precioPasaje" to "Precio ↓",
                        "-capacidad" to "Capacidad",
                    )) { (value, label) ->
                        FilterChip(
                            selected = state.ordering == value,
                            onClick  = { viewModel.setOrdering(value) },
                            label    = { Text(label, style = MaterialTheme.typography.labelSmall) },
                            colors   = FilterChipDefaults.filterChipColors(
                                selectedContainerColor    = Accent,
                                selectedLabelColor        = AccentOnDark,
                                containerColor            = Surface2,
                                labelColor                = TextSecondary,
                            ),
                        )
                    }
                }

                Spacer(Modifier.height(8.dp))

                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    item {
                        FilterChip(
                            selected = state.selectedTipo == null,
                            onClick  = { viewModel.setTipo(null) },
                            label    = { Text("Todos", style = MaterialTheme.typography.labelSmall) },
                            colors   = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = Accent,
                                selectedLabelColor     = AccentOnDark,
                                containerColor         = Surface2,
                                labelColor             = TextSecondary,
                            ),
                        )
                    }
                    val tipos = listOf("Bus", "Minibús", "Van", "Micro")
                    items(tipos) { tipo ->
                        FilterChip(
                            selected = state.selectedTipo == tipo,
                            onClick  = { viewModel.setTipo(tipo) },
                            label    = { Text(tipo, style = MaterialTheme.typography.labelSmall) },
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
            state.isLoading -> {
                Box(Modifier.fillMaxSize(), Alignment.Center) {
                    CircularProgressIndicator(color = Accent)
                }
            }
            state.error != null -> {
                Box(Modifier.fillMaxSize(), Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("❌ ${state.error}", color = Error)
                        Spacer(Modifier.height(12.dp))
                        Button(
                            onClick = viewModel::refresh,
                            colors  = ButtonDefaults.buttonColors(containerColor = Accent),
                        ) { Text("Reintentar", color = AccentOnDark) }
                    }
                }
            }
            state.vehicles.isEmpty() -> {
                Box(Modifier.fillMaxSize(), Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("🚌", style = MaterialTheme.typography.displayMedium)
                        Spacer(Modifier.height(8.dp))
                        Text("Sin resultados", color = TextPrimary, fontWeight = FontWeight.Bold)
                        Text("Intenta con otra búsqueda", color = TextSecondary)
                    }
                }
            }
            else -> {
                LazyVerticalGrid(
                    columns       = GridCells.Fixed(2),
                    state         = gridState,
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement   = Arrangement.spacedBy(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier      = Modifier.fillMaxSize(),
                ) {
                    items(state.vehicles, key = { it.id }) { vehicle ->
                        VehicleCard(
                            vehicle = vehicle,
                            onClick = { onVehicleClick(vehicle.id) },
                        )
                    }

                    if (state.isLoadingMore) {
                        item(span = { GridItemSpan(2) }) {
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
