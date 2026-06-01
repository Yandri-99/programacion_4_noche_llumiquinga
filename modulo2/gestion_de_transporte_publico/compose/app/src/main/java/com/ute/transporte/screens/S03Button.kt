package com.ute.transporte.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun S03ButtonScreen() {
    var ultimoClick by remember { mutableStateOf("(ninguno)") }

    Column(
        modifier            = Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Seccion 3 · Variantes de Button",
            style = MaterialTheme.typography.titleMedium)
        HorizontalDivider()

        Surface(
            color    = MaterialTheme.colorScheme.surfaceVariant,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text     = "Ultima accion: $ultimoClick",
                modifier = Modifier.padding(12.dp),
                style    = MaterialTheme.typography.bodyMedium
            )
        }

        Spacer(Modifier.height(4.dp))

        Button(
            onClick  = { ultimoClick = "Iniciar ruta" },
            modifier = Modifier.fillMaxWidth()
        ) { Text("Iniciar Ruta") }

        Button(
            onClick  = { ultimoClick = "Agregar parada" },
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                imageVector        = Icons.Default.Add,
                contentDescription = null,
                modifier           = Modifier.size(18.dp)
            )
            Spacer(Modifier.width(8.dp))
            Text("Agregar Parada")
        }

        OutlinedButton(
            onClick  = { ultimoClick = "Programar mantenimiento" },
            modifier = Modifier.fillMaxWidth()
        ) { Text("Programar Mantenimiento") }

        TextButton(
            onClick  = { ultimoClick = "Ver reporte" },
            modifier = Modifier.fillMaxWidth()
        ) { Text("Ver Reporte") }

        ElevatedButton(
            onClick  = { ultimoClick = "Generar informe" },
            modifier = Modifier.fillMaxWidth()
        ) { Text("Generar Informe") }

        FilledTonalButton(
            onClick  = { ultimoClick = "Filtro avanzado" },
            modifier = Modifier.fillMaxWidth()
        ) { Text("Filtro Avanzado") }

        Button(
            onClick  = { },
            enabled  = false,
            modifier = Modifier.fillMaxWidth()
        ) { Text("Bus no disponible") }

        HorizontalDivider()

        EtiquetaSeccion("IconButton")
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            IconButton(onClick = { ultimoClick = "Agregar bus" }) {
                Icon(Icons.Default.Add, contentDescription = "Agregar")
            }
            IconButton(onClick = { ultimoClick = "Eliminar bus" }) {
                Icon(Icons.Default.Delete, contentDescription = "Eliminar",
                    tint = MaterialTheme.colorScheme.error)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun S03Preview() {
    MaterialTheme { S03ButtonScreen() }
}
