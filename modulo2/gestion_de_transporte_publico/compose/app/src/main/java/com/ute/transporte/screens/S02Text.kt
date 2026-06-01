package com.ute.transporte.screens

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
        Text("Seccion 2 · Text con estilos",
            style = MaterialTheme.typography.titleMedium)
        HorizontalDivider()

        EtiquetaSeccion("1. Texto basico")
        Text("Ruta principal: Terminal Norte - Terminal Sur")

        EtiquetaSeccion("2. fontSize + fontWeight + fontStyle")
        Text("Bus 24sp",   fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Text("Horario 18sp",   fontSize = 18.sp, fontStyle  = FontStyle.Italic)
        Text("Paradas 20sp",     fontSize = 20.sp, fontWeight = FontWeight.Light)

        EtiquetaSeccion("3. Color y decoracion")
        Text("Ruta activa",
            color = Color(0xFF1976D2))
        Text("Ruta suspendida",
            textDecoration = TextDecoration.Underline)
        Text("Servicio cancelado",
            textDecoration = TextDecoration.LineThrough,
            color          = MaterialTheme.colorScheme.onSurfaceVariant)

        EtiquetaSeccion("4. maxLines + TextOverflow")
        Text(
            text     = "Este bus realiza un recorrido extenso que pasa por varias paradas importantes de la ciudad",
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text     = "El servicio de transporte publico cubre todas las rutas urbanas y suburbanas de la ciudad",
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )

        EtiquetaSeccion("5. Escala tipografica Material 3")
        Text("headlineMedium", style = MaterialTheme.typography.headlineMedium)
        Text("titleLarge",     style = MaterialTheme.typography.titleLarge)
        Text("bodyLarge",      style = MaterialTheme.typography.bodyLarge)
        Text("bodySmall",      style = MaterialTheme.typography.bodySmall)
        Text("labelSmall",     style = MaterialTheme.typography.labelSmall)

        EtiquetaSeccion("6. TextAlign")
        Text(
            text      = "Horario de atencion: 6:00 - 22:00",
            textAlign = TextAlign.Center,
            modifier  = Modifier.fillMaxWidth()
        )
        Text(
            text      = "Tarifa: $0.75",
            textAlign = TextAlign.End,
            modifier  = Modifier.fillMaxWidth()
        )
    }
}

@Composable
internal fun EtiquetaSeccion(texto: String) {
    Text(
        texto,
        style = MaterialTheme.typography.labelMedium,
        color = MaterialTheme.colorScheme.primary
    )
}

@Preview(showBackground = true)
@Composable
fun S02Preview() {
    MaterialTheme { S02TextScreen() }
}
