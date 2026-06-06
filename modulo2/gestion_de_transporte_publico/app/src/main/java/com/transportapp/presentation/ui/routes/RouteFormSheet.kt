package com.transportapp.presentation.ui.routes

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
import com.transportapp.domain.model.RoutePayload
import com.transportapp.presentation.viewmodel.RouteFormState
import com.transportapp.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RouteFormSheet(
    initial:   Route?,
    formState: RouteFormState,
    onSave:    (RoutePayload) -> Unit,
    onDismiss: () -> Unit,
) {
    val isEdit = initial != null

    var name        by remember { mutableStateOf(initial?.name        ?: "") }
    var description by remember { mutableStateOf(initial?.description ?: "") }
    var origin      by remember { mutableStateOf(initial?.origin      ?: "") }
    var destination by remember { mutableStateOf(initial?.destination ?: "") }
    var tarifa      by remember { mutableStateOf(if (initial != null) "%.2f".format(initial.tarifa) else "") }

    val isSaving    = formState is RouteFormState.Saving
    val tarifaVal   = tarifa.toDoubleOrNull()
    val nameError   = name.isNotEmpty() && name.length < 2
    val originError = origin.isNotEmpty() && origin.length < 2
    val destError   = destination.isNotEmpty() && destination.length < 2
    val tarifaError = tarifa.isNotEmpty() && (tarifaVal == null || tarifaVal <= 0)
    val canSave     = name.length >= 2 && origin.length >= 2 &&
            destination.length >= 2 && tarifaVal != null && tarifaVal > 0 && !isSaving

    LaunchedEffect(formState) {
        if (formState is RouteFormState.Success) onDismiss()
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
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Text(
                text       = if (isEdit) "Editar: ${initial?.name}" else "Nueva ruta",
                style      = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color      = TextPrimary,
            )

            if (formState is RouteFormState.Error) {
                Surface(
                    color  = Error.copy(alpha = 0.1f),
                    shape  = MaterialTheme.shapes.small,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(
                        text     = formState.message,
                        color    = Error,
                        style    = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(12.dp),
                    )
                }
            }

            OutlinedTextField(
                value         = name,
                onValueChange = { name = it },
                label         = { Text("Nombre *") },
                placeholder   = { Text("ej. Ruta Norte", color = TextFaint) },
                isError       = nameError,
                enabled       = !isSaving,
                singleLine    = true,
                modifier      = Modifier.fillMaxWidth(),
                shape         = MaterialTheme.shapes.medium,
                colors        = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor   = Accent,
                    unfocusedBorderColor = Border,
                    cursorColor          = Accent,
                    focusedLabelColor    = Accent,
                    unfocusedLabelColor  = TextSecondary,
                ),
            )

            // Origen y Destino en fila
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedTextField(
                    value         = origin,
                    onValueChange = { origin = it },
                    label         = { Text("Origen *") },
                    placeholder   = { Text("ej. Quito", color = TextFaint) },
                    isError       = originError,
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
                    value         = destination,
                    onValueChange = { destination = it },
                    label         = { Text("Destino *") },
                    placeholder   = { Text("ej. Cayambe", color = TextFaint) },
                    isError       = destError,
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

            // Tarifa
            OutlinedTextField(
                value         = tarifa,
                onValueChange = { tarifa = it },
                label         = { Text("Tarifa \$ *") },
                placeholder   = { Text("0.00", color = TextFaint) },
                isError       = tarifaError,
                enabled       = !isSaving,
                singleLine    = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier      = Modifier.fillMaxWidth(),
                shape         = MaterialTheme.shapes.medium,
                colors        = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor   = Accent,
                    unfocusedBorderColor = Border,
                    cursorColor          = Accent,
                    focusedLabelColor    = Accent,
                    unfocusedLabelColor  = TextSecondary,
                ),
            )

            // Descripción
            OutlinedTextField(
                value         = description,
                onValueChange = { description = it },
                label         = { Text("Descripci\u00F3n") },
                placeholder   = { Text("Descripci\u00F3n opcional", color = TextFaint) },
                minLines      = 3,
                maxLines      = 5,
                enabled       = !isSaving,
                modifier      = Modifier.fillMaxWidth(),
                colors        = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor   = Accent,
                    focusedLabelColor    = Accent,
                    unfocusedBorderColor = Border,
                    unfocusedLabelColor  = TextSecondary,
                ),
            )

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedButton(
                    onClick  = { if (!isSaving) onDismiss() },
                    enabled  = !isSaving,
                    modifier = Modifier.weight(1f).height(52.dp),
                    colors   = ButtonDefaults.outlinedButtonColors(contentColor = TextSecondary),
                    border   = ButtonDefaults.outlinedButtonBorder.copy(
                        brush = androidx.compose.ui.graphics.SolidColor(Border),
                    ),
                    shape    = MaterialTheme.shapes.medium,
                ) {
                    Text("Cancelar")
                }
                Button(
                    onClick  = {
                        onSave(RoutePayload(
                            name        = name.trim(),
                            description = description.trim(),
                            origin      = origin.trim(),
                            destination = destination.trim(),
                            tarifa      = tarifaVal!!,
                            image       = null,
                        ))
                    },
                    enabled  = canSave,
                    modifier = Modifier.weight(1f).height(52.dp),
                    colors   = ButtonDefaults.buttonColors(
                        containerColor         = Accent,
                        contentColor           = AccentOnDark,
                        disabledContainerColor = Accent.copy(alpha = 0.4f),
                    ),
                    shape    = MaterialTheme.shapes.medium,
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
                        else "Crear ruta",
                        fontWeight = FontWeight.Bold,
                    )
                }
            }
        }
    }
}
