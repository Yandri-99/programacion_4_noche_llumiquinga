package com.transportapp.presentation.ui.vehicles

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.unit.dp
import com.transportapp.domain.model.Route
import com.transportapp.domain.model.Vehicle
import com.transportapp.domain.model.VehiclePayload
import com.transportapp.presentation.viewmodel.VehicleFormState
import com.transportapp.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VehicleFormSheet(
    initial:    Vehicle?,
    routes:     List<Route>,
    formState:  VehicleFormState,
    onSave:     (VehiclePayload) -> Unit,
    onDismiss:  () -> Unit,
) {
    val isEdit = initial != null

    var name         by remember { mutableStateOf(initial?.name         ?: "") }
    var description  by remember { mutableStateOf(initial?.description  ?: "") }
    var placa        by remember { mutableStateOf(initial?.placa        ?: "") }
    var tipo         by remember { mutableStateOf(initial?.tipo         ?: "") }
    var capacidad    by remember { mutableStateOf(initial?.capacidad?.toString() ?: "") }
    var precioPasaje by remember { mutableStateOf(if (initial != null) "%.2f".format(initial.precioPasaje) else "") }
    var estado       by remember { mutableStateOf(initial?.estado ?: "activo") }
    var selectedRoute by remember { mutableStateOf(initial?.routeId) }
    var routeExpanded by remember { mutableStateOf(false) }
    var tipoExpanded by remember { mutableStateOf(false) }

    val isSaving       = formState is VehicleFormState.Saving
    val capacidadVal   = capacidad.toIntOrNull()
    val precioVal      = precioPasaje.toDoubleOrNull()
    val nameError      = name.isNotEmpty() && name.length < 2
    val placaError     = placa.isNotEmpty() && placa.length < 5
    val capacidadError = capacidad.isNotEmpty() && (capacidadVal == null || capacidadVal <= 0)
    val precioError    = precioPasaje.isNotEmpty() && (precioVal == null || precioVal <= 0)
    val canSave        = name.length >= 2 && placa.length >= 5 &&
            tipo.isNotEmpty() && capacidadVal != null && capacidadVal > 0 &&
            precioVal != null && precioVal > 0 &&
            selectedRoute != null && !isSaving

    val tipoOptions = listOf("Bus", "Microb\u00FAs", "Van", "Taxi", "Otro")

    LaunchedEffect(formState) {
        if (formState is VehicleFormState.Success) onDismiss()
    }

    ModalBottomSheet(
        onDismissRequest = { if (!isSaving) onDismiss() },
        containerColor   = Surface,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp)
                .padding(bottom = 32.dp)
                .navigationBarsPadding(),
            verticalArrangement = Arrangement.spacedBy(14.dp),
        ) {
            Text(
                text       = if (isEdit) "Editar: ${initial?.name}" else "Nuevo veh\u00EDculo",
                style      = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color      = TextPrimary,
            )

            if (formState is VehicleFormState.Error) {
                Surface(
                    color    = Error.copy(alpha = 0.1f),
                    shape    = MaterialTheme.shapes.small,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(
                        formState.message, color = Error,
                        style    = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(12.dp),
                    )
                }
            }

            // Nombre y Placa en fila
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedTextField(
                    value         = name,
                    onValueChange = { name = it },
                    label         = { Text("Nombre *") },
                    placeholder   = { Text("ej. Bus 001", color = TextFaint) },
                    isError       = nameError,
                    enabled       = !isSaving,
                    singleLine    = true,
                    modifier      = Modifier.weight(1f).fillMaxWidth(),
                    shape         = MaterialTheme.shapes.medium,
                    colors        = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor   = Accent,
                        unfocusedBorderColor = Border,
                        cursorColor          = Accent,
                        focusedLabelColor    = Accent,
                        unfocusedLabelColor  = TextSecondary,
                    ),
                )
                OutlinedTextField(
                    value         = placa,
                    onValueChange = { placa = it },
                    label         = { Text("Placa *") },
                    placeholder   = { Text("ABC-123", color = TextFaint) },
                    isError       = placaError,
                    enabled       = !isSaving,
                    singleLine    = true,
                    modifier      = Modifier.weight(1f).fillMaxWidth(),
                    shape         = MaterialTheme.shapes.medium,
                    colors        = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor   = Accent,
                        unfocusedBorderColor = Border,
                        cursorColor          = Accent,
                        focusedLabelColor    = Accent,
                        unfocusedLabelColor  = TextSecondary,
                    ),
                )
            }

            // Tipo
            ExposedDropdownMenuBox(
                expanded         = tipoExpanded,
                onExpandedChange = { tipoExpanded = !tipoExpanded },
            ) {
                OutlinedTextField(
                    value         = tipo.ifEmpty { "\u2014 Seleccionar \u2014" },
                    onValueChange = {},
                    readOnly      = true,
                    label         = { Text("Tipo *") },
                    trailingIcon  = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = tipoExpanded) },
                    enabled       = !isSaving,
                    colors        = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor   = Accent,
                        unfocusedBorderColor = if (tipo.isEmpty()) Error else Border,
                        cursorColor          = Accent,
                        focusedLabelColor    = Accent,
                        unfocusedLabelColor  = TextSecondary,
                    ),
                    modifier = Modifier.menuAnchor().fillMaxWidth(),
                )
                ExposedDropdownMenu(
                    expanded         = tipoExpanded,
                    onDismissRequest = { tipoExpanded = false },
                ) {
                    tipoOptions.forEach { option ->
                        DropdownMenuItem(
                            text = {
                                Text(
                                    option,
                                    color      = if (tipo == option) Accent else TextPrimary,
                                    fontWeight = if (tipo == option) FontWeight.Bold else FontWeight.Normal,
                                )
                            },
                            onClick = { tipo = option; tipoExpanded = false },
                        )
                    }
                }
            }

            // Capacidad y Precio en fila
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedTextField(
                    value         = capacidad,
                    onValueChange = { capacidad = it },
                    label         = { Text("Capacidad *") },
                    placeholder   = { Text("40", color = TextFaint) },
                    isError       = capacidadError,
                    enabled       = !isSaving,
                    singleLine    = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier      = Modifier.weight(1f).fillMaxWidth(),
                    shape         = MaterialTheme.shapes.medium,
                    colors        = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor   = Accent,
                        unfocusedBorderColor = Border,
                        cursorColor          = Accent,
                        focusedLabelColor    = Accent,
                        unfocusedLabelColor  = TextSecondary,
                    ),
                )
                OutlinedTextField(
                    value         = precioPasaje,
                    onValueChange = { precioPasaje = it },
                    label         = { Text("Precio pasaje \$ *") },
                    placeholder   = { Text("0.00", color = TextFaint) },
                    isError       = precioError,
                    enabled       = !isSaving,
                    singleLine    = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    modifier      = Modifier.weight(1f).fillMaxWidth(),
                    shape         = MaterialTheme.shapes.medium,
                    colors        = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor   = Accent,
                        unfocusedBorderColor = Border,
                        cursorColor          = Accent,
                        focusedLabelColor    = Accent,
                        unfocusedLabelColor  = TextSecondary,
                    ),
                )
            }

            // Descripción
            OutlinedTextField(
                value         = description,
                onValueChange = { description = it },
                label         = { Text("Descripci\u00F3n") },
                placeholder   = { Text("Descripci\u00F3n opcional", color = TextFaint) },
                minLines      = 3, maxLines = 4,
                enabled       = !isSaving,
                modifier      = Modifier.fillMaxWidth(),
                colors        = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor   = Accent,
                    unfocusedBorderColor = Border,
                    focusedLabelColor    = Accent,
                    unfocusedLabelColor  = TextSecondary,
                ),
            )

            // Ruta
            ExposedDropdownMenuBox(
                expanded         = routeExpanded,
                onExpandedChange = { routeExpanded = !routeExpanded },
            ) {
                OutlinedTextField(
                    value         = routes.find { it.id == selectedRoute }?.name ?: "\u2014 Seleccionar \u2014",
                    onValueChange = {},
                    readOnly      = true,
                    label         = { Text("Ruta *") },
                    trailingIcon  = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = routeExpanded) },
                    enabled       = !isSaving,
                    colors        = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor   = Accent,
                        unfocusedBorderColor = if (selectedRoute == null) Error else Border,
                        focusedLabelColor    = Accent,
                        unfocusedLabelColor  = TextSecondary,
                    ),
                    modifier = Modifier.menuAnchor().fillMaxWidth(),
                )
                ExposedDropdownMenu(
                    expanded         = routeExpanded,
                    onDismissRequest = { routeExpanded = false },
                ) {
                    routes.forEach { route ->
                        DropdownMenuItem(
                            text = {
                                Text(
                                    "${route.name} (${route.origin} \u2192 ${route.destination})",
                                    color      = if (selectedRoute == route.id) Accent else TextPrimary,
                                    fontWeight = if (selectedRoute == route.id) FontWeight.Bold else FontWeight.Normal,
                                )
                            },
                            onClick = { selectedRoute = route.id; routeExpanded = false },
                        )
                    }
                }
            }

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedButton(
                    onClick  = { if (!isSaving) onDismiss() },
                    enabled  = !isSaving,
                    modifier = Modifier.weight(1f).height(52.dp),
                    colors   = ButtonDefaults.outlinedButtonColors(contentColor = TextSecondary),
                    border   = ButtonDefaults.outlinedButtonBorder.copy(
                        brush = androidx.compose.ui.graphics.SolidColor(Border),
                    ),
                    shape = MaterialTheme.shapes.medium,
                ) { Text("Cancelar") }

                Button(
                    onClick = {
                        onSave(VehiclePayload(
                            name         = name.trim(),
                            description  = description.trim(),
                            placa        = placa.trim(),
                            tipo         = tipo,
                            capacidad    = capacidadVal!!,
                            precioPasaje = precioVal!!,
                            estado       = estado,
                            routeId      = selectedRoute!!,
                        ))
                    },
                    enabled  = canSave,
                    modifier = Modifier.weight(1f).height(52.dp),
                    colors   = ButtonDefaults.buttonColors(
                        containerColor         = Accent,
                        contentColor           = AccentOnDark,
                        disabledContainerColor = Accent.copy(alpha = 0.4f),
                    ),
                    shape = MaterialTheme.shapes.medium,
                ) {
                    if (isSaving) {
                        CircularProgressIndicator(
                            color       = AccentOnDark,
                            modifier    = Modifier.size(16.dp),
                            strokeWidth = 2.dp,
                        )
                        Spacer(Modifier.width(8.dp))
                    }
                    Text(
                        if (isSaving) "Guardando..."
                        else if (isEdit) "Guardar cambios"
                        else "Crear veh\u00EDculo",
                        fontWeight = FontWeight.Bold,
                    )
                }
            }
        }
    }
}
