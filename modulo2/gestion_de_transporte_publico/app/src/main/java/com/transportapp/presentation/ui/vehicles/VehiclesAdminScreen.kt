package com.transportapp.presentation.ui.vehicles

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.transportapp.domain.model.Vehicle
import com.transportapp.presentation.components.LoadingScreen
import com.transportapp.presentation.components.ErrorScreen
import com.transportapp.presentation.viewmodel.VehiclesAdminViewModel
import com.transportapp.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VehiclesAdminScreen(
    viewModel: VehiclesAdminViewModel = hiltViewModel(),
) {
    val state      by viewModel.state.collectAsState()
    val filtered   by viewModel.filtered.collectAsState()
    val formState  by viewModel.formState.collectAsState()
    val routes     by viewModel.routes.collectAsState()

    var showForm      by remember { mutableStateOf(false) }
    var editTarget    by remember { mutableStateOf<Vehicle?>(null) }
    var deleteTarget  by remember { mutableStateOf<Vehicle?>(null) }
    var snackMsg      by remember { mutableStateOf<String?>(null) }

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(snackMsg) {
        snackMsg?.let {
            snackbarHostState.showSnackbar(it)
            snackMsg = null
        }
    }

    Scaffold(
        snackbarHost   = { SnackbarHost(snackbarHostState) },
        containerColor = Background,
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
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
                                "Veh\u00EDculos",
                                style      = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.Bold,
                                color      = TextPrimary,
                            )
                            Text(
                                "${state.total} veh\u00EDculos",
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
                                    containerColor = Accent,
                                    contentColor   = AccentOnDark,
                                ),
                                shape          = MaterialTheme.shapes.medium,
                                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 10.dp),
                            ) {
                                Icon(Icons.Default.Add, null, modifier = Modifier.size(18.dp))
                                Spacer(Modifier.width(4.dp))
                                Text("Nuevo", fontWeight = FontWeight.Bold)
                            }
                        }
                    }

                    Spacer(Modifier.height(12.dp))

                    OutlinedTextField(
                        value         = state.search,
                        onValueChange = viewModel::setSearch,
                        placeholder   = { Text("Buscar veh\u00EDculo...", color = TextFaint) },
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
                }
            }

            when {
                state.isLoading     -> LoadingScreen("Cargando veh\u00EDculos...")
                state.error != null -> ErrorScreen(state.error!!, onRetry = viewModel::load)
                filtered.isEmpty()  -> {
                    Box(Modifier.fillMaxSize(), Alignment.Center) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("\uD83D\uDE8C", fontSize = 48.sp)
                            Spacer(Modifier.height(12.dp))
                            Text(
                                if (state.search.isBlank()) "Sin veh\u00EDculos" else "Sin resultados",
                                style      = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color      = TextPrimary,
                            )
                        }
                    }
                }
                else -> {
                    LazyColumn(
                        modifier            = Modifier.fillMaxSize(),
                        contentPadding      = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                    ) {
                        items(filtered, key = { it.id }) { vehicle ->
                            VehicleAdminCard(
                                vehicle  = vehicle,
                                onToggle = { viewModel.toggleActivo(vehicle.id, vehicle.estado != "activo") },
                                onEdit   = { editTarget = vehicle; showForm = true },
                                onDelete = { deleteTarget = vehicle },
                            )
                        }
                    }
                }
            }
        }
    }

    if (showForm) {
        VehicleFormSheet(
            initial   = editTarget,
            routes    = routes,
            formState = formState,
            onSave    = { payload ->
                if (editTarget != null) viewModel.updateVehicle(editTarget!!.id, payload)
                else viewModel.createVehicle(payload)
            },
            onDismiss = {
                showForm   = false
                editTarget = null
                viewModel.resetFormState()
            },
        )
    }

    deleteTarget?.let { vehicle ->
        AlertDialog(
            onDismissRequest = { deleteTarget = null },
            containerColor   = Surface,
            shape            = MaterialTheme.shapes.large,
            title            = { Text("\u00BFEliminar veh\u00EDculo?", color = TextPrimary) },
            text             = {
                Text(
                    "\"${vehicle.name}\" se eliminar\u00E1 permanentemente.",
                    color = TextSecondary,
                )
            },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.deleteVehicle(vehicle.id)
                    deleteTarget = null
                }) {
                    Text("Eliminar", color = Error, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { deleteTarget = null }) {
                    Text("Cancelar", color = TextSecondary)
                }
            },
        )
    }
}

@Composable
private fun VehicleAdminCard(
    vehicle:  Vehicle,
    onToggle: () -> Unit,
    onEdit:   () -> Unit,
    onDelete: () -> Unit,
) {
    Surface(
        shape  = MaterialTheme.shapes.large,
        color  = if (vehicle.estado != "inactivo") Surface else Surface.copy(alpha = 0.6f),
        modifier = Modifier.fillMaxWidth(),
    ) {
        Row(
            modifier          = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier         = Modifier
                    .size(56.dp)
                    .background(Surface2, MaterialTheme.shapes.medium),
                contentAlignment = Alignment.Center,
            ) {
                Text("\uD83D\uDE8C", fontSize = 22.sp)
            }

            Spacer(Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text       = vehicle.name,
                    style      = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold,
                    color      = TextPrimary,
                    maxLines   = 1,
                    overflow   = TextOverflow.Ellipsis,
                )
                Text(
                    text  = "${vehicle.placa} \u00B7 ${vehicle.tipo}",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextSecondary,
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment     = Alignment.CenterVertically,
                ) {
                    Text(
                        "${vehicle.capacidad} asientos",
                        style      = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.SemiBold,
                        color      = Accent,
                    )
                    Surface(
                        color = when (vehicle.estado) {
                            "activo"    -> Success.copy(alpha = 0.12f)
                            "inactivo"  -> Error.copy(alpha = 0.12f)
                            else        -> Warning.copy(alpha = 0.12f)
                        },
                        shape = MaterialTheme.shapes.extraSmall,
                    ) {
                        Text(
                            text = when (vehicle.estado) {
                                "activo"    -> "Activo"
                                "inactivo"  -> "Inactivo"
                                else        -> vehicle.estado.replaceFirstChar { it.uppercase() }
                            },
                            color = when (vehicle.estado) {
                                "activo"    -> Success
                                "inactivo"  -> Error
                                else        -> Warning
                            },
                            fontSize   = 10.sp,
                            fontWeight = FontWeight.Bold,
                            modifier   = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                        )
                    }
                }
            }

            Column(horizontalAlignment = Alignment.End) {
                Row {
                    IconButton(onClick = onEdit, modifier = Modifier.size(32.dp)) {
                        Icon(
                            Icons.Default.Edit, null,
                            tint     = TextSecondary,
                            modifier = Modifier.size(18.dp),
                        )
                    }
                    IconButton(onClick = onDelete, modifier = Modifier.size(32.dp)) {
                        Icon(
                            Icons.Default.Delete, null,
                            tint     = Error,
                            modifier = Modifier.size(18.dp),
                        )
                    }
                }
            }
        }
    }
}
