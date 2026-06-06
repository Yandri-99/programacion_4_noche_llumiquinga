package com.transportapp.presentation.ui.drivers

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
import androidx.compose.ui.unit.sp
import com.transportapp.domain.model.Driver
import com.transportapp.domain.model.DriverPayload
import com.transportapp.presentation.viewmodel.DriverFormState
import com.transportapp.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DriverFormSheet(
    initial:   Driver?,
    formState: DriverFormState,
    onSave:    (DriverPayload) -> Unit,
    onDismiss: () -> Unit,
) {
    val isEdit = initial != null

    var email      by remember { mutableStateOf(initial?.email     ?: "") }
    var nombre     by remember { mutableStateOf(initial?.nombre    ?: "") }
    var telefono   by remember { mutableStateOf(initial?.telefono  ?: "") }
    var licencia   by remember { mutableStateOf(initial?.licencia  ?: "") }
    var disponible by remember { mutableStateOf(initial?.disponible ?: true) }
    var isActive   by remember { mutableStateOf(initial?.isActive  ?: true) }
    var password   by remember { mutableStateOf("") }

    val isSaving      = formState is DriverFormState.Saving
    val nombreError   = nombre.isNotEmpty() && nombre.length < 3
    val emailError    = email.isNotEmpty() && !email.contains("@")
    val passwordError = !isEdit && password.isNotEmpty() && password.length < 8
    val canSave       = nombre.length >= 3 && email.contains("@") &&
            licencia.isNotEmpty() &&
            (isEdit || password.length >= 8) && !isSaving

    LaunchedEffect(formState) {
        if (formState is DriverFormState.Success) onDismiss()
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
                text       = if (isEdit) "Editar: ${initial?.nombre}" else "Nuevo conductor",
                style      = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color      = TextPrimary,
            )

            if (formState is DriverFormState.Error) {
                Surface(
                    color    = Error.copy(alpha = 0.1f),
                    shape    = MaterialTheme.shapes.small,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(
                        formState.message,
                        color    = Error,
                        style    = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(12.dp),
                    )
                }
            }

            // Nombre y Email en fila
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedTextField(
                    value         = nombre,
                    onValueChange = { nombre = it },
                    label         = { Text("Nombre *") },
                    placeholder   = { Text("m\u00EDnimo 3 caracteres", color = TextFaint) },
                    isError       = nombreError,
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
                    value         = email,
                    onValueChange = { email = it },
                    label         = { Text("Email *") },
                    placeholder   = { Text("correo@ejemplo.com", color = TextFaint) },
                    isError       = emailError,
                    enabled       = !isSaving,
                    singleLine    = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
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

            // Teléfono y Licencia en fila
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedTextField(
                    value         = telefono,
                    onValueChange = { telefono = it },
                    label         = { Text("Tel\u00E9fono") },
                    placeholder   = { Text("0999999999", color = TextFaint) },
                    enabled       = !isSaving,
                    singleLine    = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
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
                    value         = licencia,
                    onValueChange = { licencia = it },
                    label         = { Text("Licencia *") },
                    placeholder   = { Text("ej. ABC-123", color = TextFaint) },
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

            // Contraseña
            OutlinedTextField(
                value         = password,
                onValueChange = { password = it },
                label         = { Text(if (isEdit) "Nueva contrase\u00F1a (vac\u00EDo = no cambiar)" else "Contrase\u00F1a *") },
                placeholder   = { Text("m\u00EDnimo 8 caracteres", color = TextFaint) },
                isError       = passwordError,
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
            if (passwordError) {
                Text(
                    "M\u00EDnimo 8 caracteres",
                    color = Error,
                    style = MaterialTheme.typography.bodySmall,
                )
            }

            // Toggles Disponible y Activo
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                ToggleCard(
                    label       = "Disponible",
                    description = "Puede tomar viajes",
                    checked     = disponible,
                    onChanged   = { disponible = it },
                    enabled     = !isSaving,
                    modifier    = Modifier.weight(1f),
                )
                ToggleCard(
                    label       = "Activo",
                    description = "Puede iniciar sesi\u00F3n",
                    checked     = isActive,
                    onChanged   = { isActive = it },
                    enabled     = !isSaving,
                    modifier    = Modifier.weight(1f),
                )
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
                    shape    = MaterialTheme.shapes.medium,
                ) { Text("Cancelar") }

                Button(
                    onClick  = {
                        onSave(DriverPayload(
                            email      = email.trim(),
                            nombre     = nombre.trim(),
                            telefono   = telefono.trim(),
                            licencia   = licencia.trim(),
                            disponible = disponible,
                            isActive   = isActive,
                            password   = password.ifBlank { null },
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
                        else "Crear conductor",
                        fontWeight = FontWeight.Bold,
                    )
                }
            }
        }
    }
}

@Composable
private fun ToggleCard(
    label:       String,
    description: String,
    checked:     Boolean,
    onChanged:   (Boolean) -> Unit,
    enabled:     Boolean,
    modifier:    Modifier = Modifier,
) {
    Surface(
        color    = Surface2,
        shape    = MaterialTheme.shapes.medium,
        modifier = modifier,
    ) {
        Row(
            modifier          = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    label,
                    style      = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.SemiBold,
                    color      = TextPrimary,
                )
                Text(
                    description,
                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 10.sp),
                    color = TextSecondary,
                )
            }
            Switch(
                checked         = checked,
                onCheckedChange = onChanged,
                enabled         = enabled,
                colors          = SwitchDefaults.colors(
                    checkedThumbColor    = AccentOnDark,
                    checkedTrackColor    = Accent,
                    uncheckedTrackColor  = Surface,
                    uncheckedBorderColor = Border,
                ),
            )
        }
    }
}
