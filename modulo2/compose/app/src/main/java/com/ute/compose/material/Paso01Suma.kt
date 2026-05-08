package com.ute.compose.material

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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun Paso01SumaScreen() {
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
        HorizontalDivider()
        SumaNumeros()
    }
}

// ──  Suma de dos numeros ───────────────────────────────
@Composable
private fun SumaNumeros() {
    var numero1    by remember { mutableStateOf("0") }
    var numero2    by remember { mutableStateOf("0") }
    var resultado  by remember { mutableStateOf("0") }

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text("Formulario nuevo contacto",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.primary)

        // Numero 1
        OutlinedTextField(
            value           = numero1,
            onValueChange   = { numero1 = it },
            label           = { Text("Ingrese el primer valor") },
            leadingIcon     = { Icon(Icons.Default.Person, contentDescription = null) },

            // keyboardOptions configura el teclado del sistema operativo
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            singleLine      = true,
            modifier        = Modifier.fillMaxWidth()
        )
        // Numero 2
        OutlinedTextField(
            value           = numero2,
            onValueChange   = { numero2 = it },
            label           = { Text("Ingrese el segundo valor") },
            leadingIcon     = { Icon(Icons.Default.Person, contentDescription = null) },

            // keyboardOptions configura el teclado del sistema operativo
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            singleLine      = true,
            modifier        = Modifier.fillMaxWidth()
        )
        
        Button(
            onClick  = {
                val numero1Double = numero1.toDoubleOrNull()?:0.0
                val numero2Double = numero2.toDoubleOrNull()?:0.0
                resultado=(numero1Double + numero2Double).toString()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Sumar")
        }
        Text(text = "Resultado de $numero1 + $numero2 = $resultado")
    }
}

@Preview(showBackground = true)
@Composable
fun Paso01SumaPreview() {
    MaterialTheme { Paso01SumaScreen() }
}