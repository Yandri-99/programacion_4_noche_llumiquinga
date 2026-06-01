package com.ute.transporte.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun S04LayoutScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text("Seccion 4 · Column · Row · Box",
            style = MaterialTheme.typography.titleMedium)
        HorizontalDivider()

        EtiquetaSeccion("Column — apila paradas verticalmente")
        Column(
            modifier            = Modifier
                .fillMaxWidth()
                .background(Color(0xFFE3F2FD))
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CeldaLayout("Parada 1: Terminal Norte", Color(0xFF90CAF9))
            CeldaLayout("Parada 2: Plaza Central", Color(0xFF64B5F6))
            CeldaLayout("Parada 3: Mercado", Color(0xFF42A5F5))
        }

        EtiquetaSeccion("Row — SpaceBetween rutas")
        Row(
            modifier              = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF3E5F5))
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment     = Alignment.CenterVertically
        ) {
            Text("Ruta Norte")
            Text("Ruta Centro")
            Text("Ruta Sur")
        }

        EtiquetaSeccion("Row — SpaceEvenly horarios")
        Row(
            modifier              = Modifier
                .fillMaxWidth()
                .background(Color(0xFFE8F5E9))
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Text("6am"); Text("10am"); Text("2pm"); Text("6pm")
        }

        EtiquetaSeccion("Row + weight (distribucion 1:2:1)")
        Row(Modifier.fillMaxWidth().height(50.dp)) {
            Box(Modifier.weight(1f).fillMaxHeight().background(Color(0xFFEF9A9A)),
                contentAlignment = Alignment.Center) { Text("Pasaj") }
            Box(Modifier.weight(2f).fillMaxHeight().background(Color(0xFFE57373)),
                contentAlignment = Alignment.Center) { Text("Conductores") }
            Box(Modifier.weight(1f).fillMaxHeight().background(Color(0xFFEF5350)),
                contentAlignment = Alignment.Center) { Text("Buses") }
        }

        EtiquetaSeccion("Box — superposicion de capas")
        Box(
            modifier         = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .background(Color(0xFF1565C0)),
            contentAlignment = Alignment.Center
        ) {
            Box(Modifier.size(40.dp).background(Color(0xFF42A5F5))
                .align(Alignment.TopStart))
            Box(Modifier.size(40.dp).background(Color(0xFF1976D2))
                .align(Alignment.BottomEnd))
            Text("Mapa de rutas",
                color = Color.White,
                style = MaterialTheme.typography.labelLarge)
        }
    }
}

@Composable
private fun CeldaLayout(label: String, color: Color) {
    Box(
        modifier         = Modifier.fillMaxWidth().height(36.dp).background(color),
        contentAlignment = Alignment.Center
    ) { Text(label, style = MaterialTheme.typography.labelMedium) }
}

@Preview(showBackground = true)
@Composable
fun S04_Preview() {
    MaterialTheme { S04LayoutScreen() }
}
