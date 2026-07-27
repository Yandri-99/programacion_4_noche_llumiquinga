package com.ute.techdash.ui.hardware.sensores

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Autorenew
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.ScreenRotation
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaSensores() {
    val context = LocalContext.current
    val vm: SensoresViewModel = viewModel(factory = SensoresViewModel.factory(context))
    val state by vm.state.collectAsStateWithLifecycle()

    // Iniciar/detener con el ciclo de vida del composable
    DisposableEffect(Unit) {
        vm.iniciar()
        onDispose { vm.detener() }
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Sensores", fontWeight = FontWeight.Bold) }) }
    ) { padding ->
        LazyColumn(
            modifier        = Modifier.padding(padding).fillMaxSize(),
            contentPadding  = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Acelerómetro
            item {
                TarjetaSensor(
                    titulo  = "Acelerómetro",
                    icono   = Icons.Default.ScreenRotation,
                    activo  = state.activo
                ) {
                    state.acelerometro?.let { a ->
                        FilaValorSensor("X", a.x, "m/s²")
                        FilaValorSensor("Y", a.y, "m/s²")
                        FilaValorSensor("Z", a.z, "m/s²")
                        Spacer(Modifier.height(4.dp))
                        Text("Magnitud: ${"%.2f".format(a.magnitud)} m/s²",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.primary)
                    } ?: Text("Sin datos", color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }

            // Giroscopio
            item {
                TarjetaSensor(
                    titulo = "Giroscopio",
                    icono  = Icons.Default.Autorenew,
                    activo = state.activo
                ) {
                    state.giroscopio?.let { g ->
                        FilaValorSensor("Yaw   (Z)", g.z, "rad/s")
                        FilaValorSensor("Pitch (X)", g.x, "rad/s")
                        FilaValorSensor("Roll  (Y)", g.y, "rad/s")
                    } ?: Text("Sin datos", color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }

            // Luz ambiente
            item {
                TarjetaSensor(
                    titulo = "Luz ambiente",
                    icono  = Icons.Default.LightMode,
                    activo = state.activo
                ) {
                    state.luz?.let { l ->
                        Text(
                            "${"%.1f".format(l.lux)} lux",
                            style      = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            color      = MaterialTheme.colorScheme.primary
                        )
                        Text(l.descripcion,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Spacer(Modifier.height(8.dp))
                        LinearProgressIndicator(
                            progress = { (l.lux / 100_000f).coerceIn(0f, 1f) },
                            modifier = Modifier.fillMaxWidth()
                        )
                    } ?: Text("Sin datos", color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        }
    }
}

@Composable
private fun TarjetaSensor(
    titulo: String,
    icono:  ImageVector,
    activo: Boolean,
    contenido: @Composable ColumnScope.() -> Unit
) {
    ElevatedCard(Modifier.fillMaxWidth()) {
        Column(Modifier.padding(16.dp)) {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment     = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(icono, null, tint = MaterialTheme.colorScheme.primary)
                    Spacer(Modifier.width(8.dp))
                    Text(titulo, fontWeight = FontWeight.SemiBold)
                }
                if (activo) {
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primary)
                    )
                }
            }
            Spacer(Modifier.height(12.dp))
            contenido()
        }
    }
}

@Composable
private fun FilaValorSensor(eje: String, valor: Float, unidad: String) {
    Row(
        Modifier.fillMaxWidth().padding(vertical = 2.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(eje, color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.width(80.dp))
        Text(
            "${"%.3f".format(valor)} $unidad",
            style      = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.Medium
        )
    }
}