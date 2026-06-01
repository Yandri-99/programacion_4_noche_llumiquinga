package com.ute.transporte.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun Saludar(nombre: String) {
    Text(text = "Bienvenido, $nombre!")
}

@Composable
fun S01SaludoScreen() {
    Column(
        modifier            = Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Seccion 1 · @Composable basico",
            style = MaterialTheme.typography.titleMedium)
        HorizontalDivider()

        Saludar("Conductor Carlos")
        Saludar("Pasajero Ana")
        Saludar("Sistema de Transporte")

        HorizontalDivider()

        MensajeCondicional(mostrar = true)
        MensajeCondicional(mostrar = false)
    }
}

@Composable
private fun MensajeCondicional(mostrar: Boolean) {
    if (mostrar) {
        Text("mostrar = true -> se dibuja")
    } else {
        Text("(mostrar = false -> no se dibuja)",
            color = MaterialTheme.colorScheme.outline)
    }
}

@Preview(showBackground = true)
@Composable
fun S01_Preview() {
    MaterialTheme { S01SaludoScreen() }
}
