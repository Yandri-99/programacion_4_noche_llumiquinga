package com.transportapp.presentation.ui.drivers

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
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
import com.transportapp.domain.model.Driver
import com.transportapp.presentation.components.LoadingScreen
import com.transportapp.presentation.components.ErrorScreen
import com.transportapp.presentation.viewmodel.DriverStatusFilter
import com.transportapp.presentation.viewmodel.DriversAdminViewModel
import com.transportapp.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DriversAdminScreen(
    viewModel: DriversAdminViewModel = hiltViewModel(),
) {
    val state     by viewModel.state.collectAsState()
    val filtered  by viewModel.filtered.collectAsState()
    val formState by viewModel.formState.collectAsState()

    var showForm    by remember { mutableStateOf(false) }
    var editTarget  by remember { mutableStateOf<Driver?>(null) }
    var deleteTarget by remember { mutableStateOf<Driver?>(null) }

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
                            "Conductores",
                            style      = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            color      = TextPrimary,
                        )
                        Text(
                            "${state.total} conductores",
                            style = MaterialTheme.typography.bodySmall,
                            color = TextSecondary,
                        )
                    }
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        IconButton(onClick = viewModel::load) {
                            Icon(Icons.Default.Refresh, null, tint = TextSecondary)
                        }
                        Button(
                            onClick = { editTarget = null; showForm = true },
                            colors  = ButtonDefaults.buttonColors(
                                containerColor = Accent, contentColor = AccentOnDark,
                            ),
                            shape          = MaterialTheme.shapes.medium,
                            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 10.dp),
                        ) {
                            Icon(Icons.Default.PersonAdd, null, modifier = Modifier.size(18.dp))
                            Spacer(Modifier.width(4.dp))
                            Text("Nuevo", fontWeight = FontWeight.Bold)
                        }
                    }
                }

                Spacer(Modifier.height(12.dp))

                OutlinedTextField(
                    value         = state.search,
                    onValueChange = viewModel::setSearch,
                    placeholder   = { Text("Buscar conductor...", color = TextFaint) },
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

                Spacer(Modifier.height(10.dp))

                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(listOf(
                        "todos" to "Todos",
                        "disponible" to "Disponibles",
                        "no_disponible" to "No disponibles",
                    )) { (value, label) ->
                        FilterChip(
                            selected = state.statusFilter.name == value.uppercase(),
                            onClick  = { viewModel.setStatusFilter(DriverStatusFilter.valueOf(value.uppercase())) },
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
            state.isLoading -> LoadingScreen("Cargando conductores...")
            state.error != null -> ErrorScreen(state.error!!, onRetry = viewModel::load)
            filtered.isEmpty() -> {
                Box(Modifier.fillMaxSize(), Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("\uD83D\uDC64", fontSize = 48.sp)
                        Spacer(Modifier.height(12.dp))
                        Text(
                            if (state.search.isBlank()) "Sin conductores" else "Sin resultados",
                            style      = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color      = TextPrimary,
                        )
                    }
                }
            }
            else -> {
                LazyColumn(
                    modifier       = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                ) {
                    items(filtered, key = { it.id }) { driver ->
                        DriverAdminCard(
                            driver         = driver,
                            onToggleActive = { viewModel.toggleActive(driver.id) },
                            onToggleDisp   = { viewModel.toggleDisponible(driver.id, !driver.disponible) },
                            onEdit         = { editTarget = driver; showForm = true },
                            onDelete       = { deleteTarget = driver },
                        )
                    }
                }
            }
        }
    }

    if (showForm) {
        DriverFormSheet(
            initial   = editTarget,
            formState = formState,
            onSave    = { payload ->
                if (editTarget != null) viewModel.updateDriver(editTarget!!.id, payload)
                else viewModel.createDriver(payload)
            },
            onDismiss = {
                showForm   = false
                editTarget = null
                viewModel.resetFormState()
            },
        )
    }

    deleteTarget?.let { driver ->
        AlertDialog(
            onDismissRequest = { deleteTarget = null },
            containerColor   = Surface,
            shape            = MaterialTheme.shapes.large,
            title            = { Text("\u00BFEliminar conductor?", color = TextPrimary) },
            text             = {
                Text(
                    "\"${driver.nombre}\" se eliminar\u00E1 permanentemente. Esta acci\u00F3n no se puede deshacer.",
                    color = TextSecondary,
                )
            },
            confirmButton    = {
                TextButton(onClick = {
                    viewModel.deleteDriver(driver.id)
                    deleteTarget = null
                }) { Text("Eliminar", color = Error, fontWeight = FontWeight.Bold) }
            },
            dismissButton    = {
                TextButton(onClick = { deleteTarget = null }) {
                    Text("Cancelar", color = TextSecondary)
                }
            },
        )
    }
}

@Composable
private fun DriverAdminCard(
    driver:         Driver,
    onToggleActive: () -> Unit,
    onToggleDisp:   () -> Unit,
    onEdit:         () -> Unit,
    onDelete:       () -> Unit,
) {
    Surface(
        shape  = MaterialTheme.shapes.large,
        color  = if (driver.isActive) Surface else Surface.copy(alpha = 0.55f),
        modifier = Modifier.fillMaxWidth(),
    ) {
        Row(
            modifier          = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier         = Modifier
                    .size(46.dp)
                    .background(
                        brush = if (driver.disponible)
                            androidx.compose.ui.graphics.Brush.linearGradient(
                                listOf(Accent, AccentLight)
                            )
                        else
                            androidx.compose.ui.graphics.Brush.linearGradient(
                                listOf(Surface2, Border)
                            ),
                        shape = CircleShape,
                    ),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text       = driver.nombre.firstOrNull()?.uppercaseChar()?.toString() ?: "?",
                    color      = if (driver.disponible) AccentOnDark else TextSecondary,
                    fontWeight = FontWeight.Bold,
                    fontSize   = 18.sp,
                )
            }

            Spacer(Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(
                    verticalAlignment     = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                ) {
                    Text(
                        text       = driver.nombre,
                        style      = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.SemiBold,
                        color      = TextPrimary,
                    )
                    if (driver.disponible) {
                        Surface(
                            color  = Success.copy(alpha = 0.15f),
                            shape  = MaterialTheme.shapes.extraSmall,
                        ) {
                            Text(
                                "Disponible",
                                color      = Success,
                                fontSize   = 10.sp,
                                fontWeight = FontWeight.Bold,
                                modifier   = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                            )
                        }
                    } else {
                        Surface(
                            color  = Warning.copy(alpha = 0.12f),
                            shape  = MaterialTheme.shapes.extraSmall,
                        ) {
                            Text(
                                "No disponible",
                                color      = Warning,
                                fontSize   = 10.sp,
                                fontWeight = FontWeight.Bold,
                                modifier   = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                            )
                        }
                    }
                    if (!driver.isActive) {
                        Surface(
                            color  = Error.copy(alpha = 0.12f),
                            shape  = MaterialTheme.shapes.extraSmall,
                        ) {
                            Text(
                                "Inactivo",
                                color      = Error,
                                fontSize   = 10.sp,
                                fontWeight = FontWeight.Bold,
                                modifier   = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                            )
                        }
                    }
                }
                Text(
                    text  = driver.email,
                    style = MaterialTheme.typography.bodySmall,
                    color = TextSecondary,
                )
                Text(
                    text  = "Licencia: ${driver.licencia}",
                    style = MaterialTheme.typography.bodySmall,
                    color = Accent,
                    fontWeight = FontWeight.SemiBold,
                )
            }

            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(2.dp)) {
                    IconButton(onClick = onToggleDisp, modifier = Modifier.size(32.dp)) {
                        Icon(
                            imageVector = if (driver.disponible) Icons.Default.CheckCircle
                            else Icons.Default.Cancel,
                            contentDescription = if (driver.disponible) "No disponible" else "Disponible",
                            tint     = if (driver.disponible) Success else TextFaint,
                            modifier = Modifier.size(18.dp),
                        )
                    }
                    IconButton(onClick = onToggleActive, modifier = Modifier.size(32.dp)) {
                        Icon(
                            imageVector = if (driver.isActive) Icons.Default.ToggleOn
                            else Icons.Default.ToggleOff,
                            contentDescription = if (driver.isActive) "Desactivar" else "Activar",
                            tint     = if (driver.isActive) Success else TextFaint,
                            modifier = Modifier.size(22.dp),
                        )
                    }
                    IconButton(onClick = onEdit, modifier = Modifier.size(32.dp)) {
                        Icon(
                            Icons.Default.Edit, contentDescription = "Editar",
                            tint = TextSecondary, modifier = Modifier.size(18.dp),
                        )
                    }
                    IconButton(onClick = onDelete, modifier = Modifier.size(32.dp)) {
                        Icon(
                            Icons.Default.PersonRemove, contentDescription = "Eliminar",
                            tint = Error, modifier = Modifier.size(18.dp),
                        )
                    }
                }
            }
        }
    }
}
