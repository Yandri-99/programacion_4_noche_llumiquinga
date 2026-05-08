package com.ute.compose.screens

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
            .verticalScroll(rememberScrollState()),  // scroll para ver todo el contenido
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text("Sección 4 · Column · Row · Box",
            style = MaterialTheme.typography.titleMedium)
        HorizontalDivider()

        // ── COLUMN ─────────────────────────────────────────────────────────
        EtiquetaSeccion("Column — apila verticalmente")
        Column(
            modifier            = Modifier
                .fillMaxWidth()
                .background(Color(0xFFE3F2FD))
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CeldaLayout("Elemento 1", Color(0xFF90CAF9))
            CeldaLayout("Elemento 2", Color(0xFF64B5F6))
            CeldaLayout("Elemento 3", Color(0xFF42A5F5))
        }

        // ── ROW con Arrangement ────────────────────────────────────────────
        EtiquetaSeccion("Row — SpaceBetween")
        Row(
            modifier              = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF3E5F5))
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment     = Alignment.CenterVertically
        ) {
            Text("Izquierda")
            Text("Centro")
            Text("Derecha")
        }

        EtiquetaSeccion("Row — SpaceEvenly")
        Row(
            modifier              = Modifier
                .fillMaxWidth()
                .background(Color(0xFFE8F5E9))
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Text("A"); Text("B"); Text("C"); Text("D")
        }

        // ── ROW con weight ─────────────────────────────────────────────────
        EtiquetaSeccion("Row + weight (distribución proporcional 1:2:1)")
        Row(Modifier.fillMaxWidth().height(50.dp)) {
            Box(Modifier.weight(1f).fillMaxHeight().background(Color(0xFFEF9A9A)),
                contentAlignment = Alignment.Center) { Text("1") }
            Box(Modifier.weight(2f).fillMaxHeight().background(Color(0xFFE57373)),
                contentAlignment = Alignment.Center) { Text("2") }
            Box(Modifier.weight(1f).fillMaxHeight().background(Color(0xFFEF5350)),
                contentAlignment = Alignment.Center) { Text("1") }
        }

        // ── BOX ────────────────────────────────────────────────────────────
        EtiquetaSeccion("Box — superpone capas con align por capa")
        Box(
            modifier         = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .background(Color(0xFF1565C0)),
            contentAlignment = Alignment.Center   // alineación por defecto
        ) {
            // Capa 1: esquina superior izquierda
            Box(Modifier.size(40.dp).background(Color(0xFF42A5F5))
                .align(Alignment.TopStart))
            // Capa 2: esquina inferior derecha
            Box(Modifier.size(40.dp).background(Color(0xFF1976D2))
                .align(Alignment.BottomEnd))
            // Capa 3: centrada (por defecto del Box padre)
            Text("Texto encima de todo",
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