package com.ute.transporte.material

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
        Text("Paso 1 · Suma de distancias",
            style = MaterialTheme.typography.titleMedium)
        HorizontalDivider()
        HorizontalDivider()
        SumaNumeros()
    }
}

@Composable
private fun SumaNumeros() {
    var distancia1 by remember { mutableStateOf("0") }
    var distancia2 by remember { mutableStateOf("0") }
    var resultado  by remember { mutableStateOf("0") }

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text("Calcular distancia total",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.primary)

        OutlinedTextField(
            value           = distancia1,
            onValueChange   = { distancia1 = it },
            label           = { Text("Distancia del tramo 1 (km)") },
            leadingIcon     = { Icon(Icons.Default.Route, contentDescription = null) },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            singleLine      = true,
            modifier        = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value           = distancia2,
            onValueChange   = { distancia2 = it },
            label           = { Text("Distancia del tramo 2 (km)") },
            leadingIcon     = { Icon(Icons.Default.Route, contentDescription = null) },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            singleLine      = true,
            modifier        = Modifier.fillMaxWidth()
        )

        Button(
            onClick  = {
                val d1 = distancia1.toDoubleOrNull()?:0.0
                val d2 = distancia2.toDoubleOrNull()?:0.0
                resultado = (d1 + d2).toString()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Sumar distancias")
        }
        Text(text = "Distancia total: $resultado km")
    }
}

@Preview(showBackground = true)
@Composable
fun Paso01SumaPreview() {
    MaterialTheme { Paso01SumaScreen() }
}
