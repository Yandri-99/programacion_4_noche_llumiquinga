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
fun Paso07Ejercicio() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Text("Ejercicio · Cálculo de pasaje",
            style = MaterialTheme.typography.titleMedium)
        HorizontalDivider()
        HorizontalDivider()
        CalcularPasaje()
    }
}

@Composable
private fun CalcularPasaje() {
    var origen    by remember { mutableStateOf("") }
    var distancia by remember { mutableStateOf("0") }
    var pasajeros by remember { mutableStateOf("0") }
    var tarifa    by remember { mutableStateOf("0") }
    var descuento by remember { mutableStateOf("0") }
    var total     by remember { mutableStateOf("0") }

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text("Calculadora de pasaje",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.primary)

        OutlinedTextField(
            value           = origen,
            onValueChange   = { origen = it },
            label           = { Text("Lugar de origen") },
            leadingIcon     = { Icon(Icons.Default.LocationOn, contentDescription = null) },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            singleLine      = true,
            modifier        = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value           = distancia,
            onValueChange   = { distancia = it },
            label           = { Text("Distancia (km)") },
            leadingIcon     = { Icon(Icons.Default.Route, contentDescription = null) },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            singleLine      = true,
            modifier        = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value           = pasajeros,
            onValueChange   = { pasajeros = it },
            label           = { Text("Número de pasajeros") },
            leadingIcon     = { Icon(Icons.Default.People, contentDescription = null) },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            singleLine      = true,
            modifier        = Modifier.fillMaxWidth()
        )

        Button(
            onClick  = {
                val distanciaDouble = distancia.toDoubleOrNull()?:0.0
                val pasajerosInt = pasajeros.toIntOrNull()?:1
                val tarifaCalculada = distanciaDouble * 0.15 * pasajerosInt
                var descuentoCalculado = 0.0
                if (pasajerosInt >= 5) {
                    descuentoCalculado = tarifaCalculada * 0.10
                } else if (pasajerosInt >= 3) {
                    descuentoCalculado = tarifaCalculada * 0.05
                }
                val totalCalculado = tarifaCalculada - descuentoCalculado

                tarifa = tarifaCalculada.toString()
                descuento = descuentoCalculado.toString()
                total = totalCalculado.toString()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text="Calcular pasaje")
        }
        Text(text = "Origen: $origen")
        Text(text = "Tarifa base: \$$tarifa")
        Text(text = "Descuento: \$$descuento")
        Text(text = "Total a pagar: \$$total")
    }
}

@Preview(showBackground = true)
@Composable
fun Paso07EjercicioPreview() {
    MaterialTheme {
        Paso07Ejercicio()
    }
}
