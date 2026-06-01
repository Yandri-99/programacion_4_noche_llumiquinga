package com.ute.transporte.screens

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
        Text("Seccion 7 · State Hoisting",
            style = MaterialTheme.typography.titleMedium)
        HorizontalDivider()

        DemoEstadoAtrapado()
        HorizontalDivider()
        DemoEstadoElevado()
    }
}

@Composable
private fun DemoEstadoAtrapado() {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        EtiquetaSeccion("Estado atrapado — el padre no puede leerlo")

        Text(
            "El estado vive dentro del boton. El padre no sabe cuantas veces " +
                    "fue presionado.",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        BotonAtrapado()

        Text(
            "El padre no puede mostrar el conteo aqui",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.error
        )
    }
}

@Composable
private fun BotonAtrapado() {
    var cuenta by remember { mutableStateOf(0) }
    Button(onClick = { cuenta++ }) {
        Text("Presionado $cuenta veces (estado atrapado)")
    }
}

@Composable
private fun DemoEstadoElevado() {
    var seleccion by remember { mutableStateOf<String?>(null) }
    var historial by remember { mutableStateOf(listOf<String>()) }

    val opciones = listOf("Ruta Norte", "Ruta Centro", "Ruta Sur", "Ruta Expreso")

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        EtiquetaSeccion("Estado elevado — el padre coordina todo")

        Text(
            "El hijo solo notifica que ruta fue seleccionada. " +
                    "El padre actualiza la seleccion Y el historial.",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        SelectorRutas(
            opciones   = opciones,
            seleccion  = seleccion,
            onSeleccion = { opcion ->
                seleccion = opcion
                historial = (historial + opcion).takeLast(4)
            }
        )

        seleccion?.let { sel ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFFBBDEFB)),
                contentAlignment = Alignment.Center
            ) {
                Text("Ruta seleccionada: $sel",
                    style = MaterialTheme.typography.labelLarge)
            }
        }

        if (historial.isNotEmpty()) {
            Text(
                "Historial: ${historial.joinToString(" -> ")}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun SelectorRutas(
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
