package com.ute.compose.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun S07StateHoistingScreen() {
    Column(
        modifier            = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text("Sección 7 · State Hoisting",
            style = MaterialTheme.typography.titleMedium)
        HorizontalDivider()

        DemoEstadoAtrapado()
        HorizontalDivider()
        DemoEstadoElevado()
    }
}

// ── ❌ Anti-patrón: estado atrapado dentro del componente ────────────────────
@Composable
private fun DemoEstadoAtrapado() {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        EtiquetaSeccion("❌ Estado atrapado — el padre no puede leerlo")

        Text(
            "El estado vive dentro del botón. El padre no sabe cuántas veces " +
                    "fue presionado ni puede usarlo para nada.",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        // El estado está atrapado — nadie más puede acceder a él
        BotonAtrapado()

        // El padre intenta mostrar el conteo pero no puede
        Text(
            "El padre no puede mostrar el conteo aquí ❌",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.error
        )
    }
}

@Composable
private fun BotonAtrapado() {
    // 'cuenta' está encerrado aquí — el padre no tiene acceso
    var cuenta by remember { mutableStateOf(0) }
    Button(onClick = { cuenta++ }) {
        Text("Presionado $cuenta veces (estado atrapado)")
    }
}

// ── ✅ Patrón correcto: estado elevado al padre ──────────────────────────────
@Composable
private fun DemoEstadoElevado() {
    // El estado vive aquí — el padre puede usarlo para múltiples propósitos
    var seleccion by remember { mutableStateOf<String?>(null) }
    var historial by remember { mutableStateOf(listOf<String>()) }

    val opciones = listOf("🔴 Rojo", "🟢 Verde", "🔵 Azul", "🟡 Amarillo")

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        EtiquetaSeccion("✅ Estado elevado — el padre coordina todo")

        Text(
            "El hijo solo notifica qué fue seleccionado. " +
                    "El padre actualiza la selección Y el historial.",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        // El padre pasa el valor actual y un callback
        // El hijo es "tonto" — no decide qué hacer con el click
        SelectorOpciones(
            opciones   = opciones,
            seleccion  = seleccion,
            onSeleccion = { opcion ->
                // El padre decide qué hacer con el evento:
                seleccion = opcion
                historial = (historial + opcion).takeLast(4)  // máximo 4 entradas
            }
        )

        // El padre usa el mismo estado para dos cosas distintas
        seleccion?.let { sel ->
            val color = when {
                "Rojo"     in sel -> Color(0xFFFFCDD2)
                "Verde"    in sel -> Color(0xFFC8E6C9)
                "Azul"     in sel -> Color(0xFFBBDEFB)
                "Amarillo" in sel -> Color(0xFFFFF9C4)
                else              -> Color.Transparent
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(color),
                contentAlignment = Alignment.Center
            ) {
                Text("Seleccionado: $sel",
                    style = MaterialTheme.typography.labelLarge)
            }
        }

        if (historial.isNotEmpty()) {
            Text(
                "Historial: ${historial.joinToString(" → ")}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

// Composable stateless — recibe todo por parámetros, no tiene estado propio
// Fácil de testear: solo necesitas pasarle datos y lambdas
@Composable
private fun SelectorOpciones(
    opciones:    List<String>,
    seleccion:   String?,
    onSeleccion: (String) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        opciones.forEach { opcion ->
            val estaSeleccionado = seleccion == opcion
            Button(
                onClick  = { onSeleccion(opcion) },
                modifier = Modifier.fillMaxWidth(),
                colors   = if (estaSeleccionado)
                    ButtonDefaults.buttonColors()
                else
                    ButtonDefaults.outlinedButtonColors()
            ) {
                Text(opcion)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun S07_Preview() {
    MaterialTheme { S07StateHoistingScreen() }
}