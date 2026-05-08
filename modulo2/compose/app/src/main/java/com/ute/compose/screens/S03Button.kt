package com.ute.compose.screens

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
    // Estado para mostrar cuál botón fue presionado
    var ultimoClick by remember { mutableStateOf("(ninguno)") }

    Column(
        modifier            = Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Sección 3 · Variantes de Button",
            style = MaterialTheme.typography.titleMedium)
        HorizontalDivider()

        // Panel de feedback — muestra el último click
        Surface(
            color    = MaterialTheme.colorScheme.surfaceVariant,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text     = "Último click: $ultimoClick",
                modifier = Modifier.padding(12.dp),
                style    = MaterialTheme.typography.bodyMedium
            )
        }

        Spacer(Modifier.height(4.dp))

        Button(
            onClick  = { ultimoClick = "Button (Primary)" },
            modifier = Modifier.fillMaxWidth()
        ) { Text("Button — Primary") }

        // Button con ícono dentro del slot de contenido
        Button(
            onClick  = { ultimoClick = "Button con ícono" },
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                imageVector        = Icons.Default.Add,
                contentDescription = null,
                modifier           = Modifier.size(18.dp)
            )
            Spacer(Modifier.width(8.dp))
            Text("Button con ícono")
        }

        OutlinedButton(
            onClick  = { ultimoClick = "OutlinedButton" },
            modifier = Modifier.fillMaxWidth()
        ) { Text("OutlinedButton") }

        TextButton(
            onClick  = { ultimoClick = "TextButton" },
            modifier = Modifier.fillMaxWidth()
        ) { Text("TextButton") }

        ElevatedButton(
            onClick  = { ultimoClick = "ElevatedButton" },
            modifier = Modifier.fillMaxWidth()
        ) { Text("ElevatedButton") }

        FilledTonalButton(
            onClick  = { ultimoClick = "FilledTonalButton" },
            modifier = Modifier.fillMaxWidth()
        ) { Text("FilledTonalButton") }

        // enabled = false → el botón no dispara onClick, apariencia atenuada
        Button(
            onClick  = { },
            enabled  = false,
            modifier = Modifier.fillMaxWidth()
        ) { Text("Deshabilitado (enabled = false)") }

        HorizontalDivider()

        // IconButton — solo ícono, sin texto
        EtiquetaSeccion("IconButton")
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            IconButton(onClick = { ultimoClick = "IconButton Add" }) {
                Icon(Icons.Default.Add, contentDescription = "Agregar")
            }
            IconButton(onClick = { ultimoClick = "IconButton Delete" }) {
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