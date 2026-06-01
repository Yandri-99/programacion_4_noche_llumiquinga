package com.ute.transporte.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun S06EstadoScreen() {
    Column(
        modifier            = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Text("Seccion 6 · Estado y recomposicion",
            style = MaterialTheme.typography.titleMedium)
        HorizontalDivider()

        DemoContadorS6()
        HorizontalDivider()
        DemoEstadoDerivado()
    }
}

@Composable
private fun DemoContadorS6() {
    var cuenta by remember { mutableStateOf(0) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        EtiquetaSeccion("Contador de pasajeros — remember + mutableStateOf")

        Text(
            text       = "$cuenta",
            style      = MaterialTheme.typography.displayMedium,
            fontWeight = FontWeight.Bold
        )

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = { cuenta-- }) { Text("-") }
            Button(onClick = { cuenta++ }) { Text("+") }
            OutlinedButton(onClick = { cuenta = 0 }) { Text("Reset") }
        }

        Text(
            "Solo el numero se recompone al hacer click",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun DemoEstadoDerivado() {
    var nivel by remember { mutableStateOf(0) }
    val max   = 5

    val porcentaje = nivel.toFloat() / max
    val etiquetaNivel = when {
        nivel == 0    -> "Sin rutas"
        nivel <= 2    -> "Principiante"
        nivel <= 4    -> "Intermedio"
        else          -> "Avanzado"
    }

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        EtiquetaSeccion("Estado derivado — nivel de conductor")

        Text(
            "$etiquetaNivel (nivel $nivel/$max)",
            style      = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold
        )

        LinearProgressIndicator(
            progress = { porcentaje },
            modifier = Modifier
                .fillMaxWidth()
                .height(12.dp)
                .clip(RoundedCornerShape(6.dp))
        )

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedButton(
                onClick  = { if (nivel > 0) nivel-- },
                enabled  = nivel > 0
            ) { Text("Bajar nivel") }

            Button(
                onClick  = { if (nivel < max) nivel++ },
                enabled  = nivel < max
            ) { Text("Subir nivel") }
        }

        Text(
            "porcentaje = ${"%.0f".format(porcentaje * 100)}% " +
                    "— derivado de nivel, sin estado propio",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Preview(showBackground = true)
@Composable
fun S06_Preview() {
    MaterialTheme { S06EstadoScreen() }
}
