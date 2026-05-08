package com.ute.compose.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun Saludo(nombre: String) {
    Text(text = "Hola, $nombre!")
}

@Composable
fun S01SaludoScreen() {
    Column(
        modifier            = Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Sección 1 · @Composable básico",
            style = MaterialTheme.typography.titleMedium)
        HorizontalDivider()

        // El mismo composable, distintos argumentos
        Saludo("Ana")
        Saludo("Luis")
        Saludo("Kotlin")

        HorizontalDivider()

        MensajeCondicional(mostrar = true)
        MensajeCondicional(mostrar = false)
    }
}

@Composable
private fun MensajeCondicional(mostrar: Boolean) {
    if (mostrar) {
        Text("✅ mostrar = true  → se dibuja")
    } else {
        // Este Text NUNCA aparece en pantalla — el else es solo para claridad
        Text("(mostrar = false → este Text existe en código pero no se dibuja)",
            color = MaterialTheme.colorScheme.outline)
    }
}

@Preview(showBackground = true)
@Composable
fun S01_Preview() {
    MaterialTheme { S01SaludoScreen() }
}