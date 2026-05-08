package com.ute.compose.ui.theme.viewmodel

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun Paso01_TextFieldScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Text("Paso 1 · TextField y OutlinedTextField",
            style = MaterialTheme.typography.titleMedium)
        HorizontalDivider()

        DemoBusqueda()
        HorizontalDivider()
        DemoFormularioContacto()
    }
}

// ── Demo 1: campo de búsqueda con limpiar ────────────────────────────────────
@Composable
private fun DemoBusqueda() {
    var busqueda by remember { mutableStateOf("") }

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text("Búsqueda con ícono y botón limpiar",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.primary)

        OutlinedTextField(
            value         = busqueda,
            onValueChange = { busqueda = it },
            placeholder   = { Text("Buscar contacto...") },
            leadingIcon   = { Icon(Icons.Default.Search, contentDescription = null) },
            // trailingIcon solo aparece cuando hay texto — evita un ícono permanente inútil
            trailingIcon  = {
                if (busqueda.isNotEmpty()) {
                    IconButton(onClick = { busqueda = "" }) {
                        Icon(Icons.Default.Clear, contentDescription = "Limpiar búsqueda")
                    }
                }
            },
            singleLine = true,
            modifier   = Modifier.fillMaxWidth()
        )

        // El texto de ayuda refleja el estado en tiempo real
        Text(
            text  = if (busqueda.isBlank()) "Escribe para filtrar"
            else "Buscando: \"$busqueda\"",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

// ── Demo 2: formulario con validación completa ───────────────────────────────
@Composable
private fun DemoFormularioContacto() {
    var nombre     by remember { mutableStateOf("") }
    var email      by remember { mutableStateOf("") }
    var telefono   by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    var verPass    by remember { mutableStateOf(false) }

    // Validaciones derivadas del estado — se recalculan en cada recomposición
    val nombreValido   = nombre.trim().length >= 2
    val emailValido    = email.contains("@") && email.contains(".")
    val telefonoValido = telefono.length >= 7 && telefono.all { it.isDigit() || it == '+' || it == ' ' }
    val passValida     = contrasena.length >= 8

    val formularioValido = nombreValido && emailValido && telefonoValido && passValida

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text("Formulario nuevo contacto",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.primary)

        // Nombre — validación básica de longitud
        OutlinedTextField(
            value           = nombre,
            onValueChange   = { nombre = it },
            label           = { Text("Nombre completo") },
            leadingIcon     = { Icon(Icons.Default.Person, contentDescription = null) },
            isError         = nombre.isNotEmpty() && !nombreValido,
            supportingText  = {
                when {
                    nombre.isNotEmpty() && !nombreValido ->
                        Text("Mínimo 2 caracteres", color = MaterialTheme.colorScheme.error)
                    nombreValido ->
                        Text("✓ Nombre válido", color = MaterialTheme.colorScheme.primary)
                    else -> Text("Requerido")
                }
            },
            // keyboardOptions configura el teclado del sistema operativo
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            singleLine      = true,
            modifier        = Modifier.fillMaxWidth()
        )

        // Email — KeyboardType.Email activa el teclado con @ visible
        OutlinedTextField(
            value           = email,
            onValueChange   = { email = it },
            label           = { Text("Correo electrónico") },
            placeholder     = { Text("usuario@dominio.com") },
            leadingIcon     = { Icon(Icons.Default.Email, contentDescription = null) },
            isError         = email.isNotEmpty() && !emailValido,
            supportingText  = {
                if (email.isNotEmpty() && !emailValido)
                    Text("Formato inválido (requiere @ y dominio)",
                        color = MaterialTheme.colorScheme.error)
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction    = ImeAction.Next
            ),
            singleLine  = true,
            modifier    = Modifier.fillMaxWidth()
        )

        // Teléfono — KeyboardType.Phone activa teclado numérico
        OutlinedTextField(
            value           = telefono,
            onValueChange   = { telefono = it },
            label           = { Text("Teléfono") },
            placeholder     = { Text("+593 99 999 9999") },
            leadingIcon     = { Icon(Icons.Default.Phone, contentDescription = null) },
            isError         = telefono.isNotEmpty() && !telefonoValido,
            supportingText  = {
                if (telefono.isNotEmpty() && !telefonoValido)
                    Text("Mínimo 7 dígitos",
                        color = MaterialTheme.colorScheme.error)
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Phone,
                imeAction    = ImeAction.Next
            ),
            singleLine  = true,
            modifier    = Modifier.fillMaxWidth()
        )

        // Contraseña — VisualTransformation oculta el texto
        // El trailingIcon alterna entre ocultar y mostrar
        OutlinedTextField(
            value           = contrasena,
            onValueChange   = { contrasena = it },
            label           = { Text("Contraseña") },
            leadingIcon     = { Icon(Icons.Default.Lock, contentDescription = null) },
            trailingIcon    = {
                IconButton(onClick = { verPass = !verPass }) {
                    Icon(
                        imageVector        = if (verPass) Icons.Default.VisibilityOff
                        else Icons.Default.Visibility,
                        contentDescription = if (verPass) "Ocultar contraseña"
                        else "Mostrar contraseña"
                    )
                }
            },
            // PasswordVisualTransformation reemplaza cada char por un punto
            // VisualTransformation.None muestra el texto tal cual
            visualTransformation = if (verPass) VisualTransformation.None
            else PasswordVisualTransformation(),
            isError         = contrasena.isNotEmpty() && !passValida,
            supportingText  = {
                Text(
                    text  = "${contrasena.length}/8 caracteres mínimos",
                    color = if (passValida) MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.onSurfaceVariant
                )
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction    = ImeAction.Done
            ),
            singleLine  = true,
            modifier    = Modifier.fillMaxWidth()
        )

        Button(
            onClick  = { /* Paso 6: mostrará un diálogo de confirmación */ },
            enabled  = formularioValido,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (formularioValido) "Guardar contacto ✓" else "Completa todos los campos")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Paso01_Preview() {
    MaterialTheme { Paso01_TextFieldScreen() }
}