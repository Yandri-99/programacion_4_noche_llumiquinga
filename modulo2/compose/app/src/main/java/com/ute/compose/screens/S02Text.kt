package com.ute.compose.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun S02TextScreen() {
    Column(
        modifier            = Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Sección 2 · Text con estilos",
            style = MaterialTheme.typography.titleMedium)
        HorizontalDivider()

        EtiquetaSeccion("1. Texto básico")
        Text("Texto simple sin propiedades adicionales")

        EtiquetaSeccion("2. fontSize + fontWeight + fontStyle")
        Text("Negrita 24sp",   fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Text("Cursiva 18sp",   fontSize = 18.sp, fontStyle  = FontStyle.Italic)
        Text("Light 20sp",     fontSize = 20.sp, fontWeight = FontWeight.Light)

        EtiquetaSeccion("3. Color y decoración")
        Text("Texto en azul",
            color = Color(0xFF1976D2))
        Text("Subrayado",
            textDecoration = TextDecoration.Underline)
        Text("Tachado",
            textDecoration = TextDecoration.LineThrough,
            color          = MaterialTheme.colorScheme.onSurfaceVariant)

        EtiquetaSeccion("4. maxLines + TextOverflow")
        Text(
            text     = "Este texto es muy largo y definitivamente no cabe en una sola línea del dispositivo móvil moderno",
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text     = "Este texto está limitado a dos líneas y el resto se corta con puntos suspensivos al final del segundo renglón",
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )

        EtiquetaSeccion("5. Escala tipográfica Material 3")
        Text("headlineMedium", style = MaterialTheme.typography.headlineMedium)
        Text("titleLarge",     style = MaterialTheme.typography.titleLarge)
        Text("bodyLarge",      style = MaterialTheme.typography.bodyLarge)
        Text("bodySmall",      style = MaterialTheme.typography.bodySmall)
        Text("labelSmall",     style = MaterialTheme.typography.labelSmall)

        EtiquetaSeccion("6. TextAlign")
        Text(
            text      = "Texto centrado en todo el ancho disponible",
            textAlign = TextAlign.Center,
            modifier  = Modifier.fillMaxWidth()
        )
        Text(
            text      = "Alineado a la derecha",
            textAlign = TextAlign.End,
            modifier  = Modifier.fillMaxWidth()
        )
    }
}

// Composable de etiqueta reutilizable — se declara internal para que
// otros archivos del mismo módulo puedan usarla
@Composable
internal fun EtiquetaSeccion(texto: String) {
    Text(
        text  = texto,
        style = MaterialTheme.typography.labelMedium,
        color = MaterialTheme.colorScheme.primary
    )
}

@Preview(showBackground = true)
@Composable
fun S02Preview() {
    MaterialTheme { S02TextScreen() }
}