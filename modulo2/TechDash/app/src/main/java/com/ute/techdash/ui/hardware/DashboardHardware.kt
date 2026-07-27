// 📄 Programa completo → ui/hardware/DashboardHardware.kt
package com.ute.techdash.ui.hardware

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SignalCellularAlt
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.material.icons.filled.WifiOff
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ute.techdash.ui.hardware.red.BannerConectividad
import com.ute.techdash.ui.hardware.red.ConectividadRepository
import com.ute.techdash.ui.hardware.red.EstadoRed
import com.ute.techdash.ui.hardware.sensores.DatoAcelerometro
import com.ute.techdash.ui.hardware.sensores.DatoLuz
import com.ute.techdash.ui.hardware.sensores.SensoresRepository
import kotlinx.coroutines.flow.catch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardHardware() {
    val context = LocalContext.current
    val repositorioRed      = remember { ConectividadRepository(context) }
    val repositorioSensores = remember { SensoresRepository(context) }

    val estadoRed by repositorioRed.conectividadFlow()
        .collectAsStateWithLifecycle(initialValue = repositorioRed.estadoActual())

    val acelerometro by repositorioSensores.acelerometroFlow()
        .catch { emit(DatoAcelerometro(0f, 0f, 0f)) }
        .collectAsStateWithLifecycle(initialValue = null)

    val luz by repositorioSensores.luzAmbienteFlow()
        .catch { emit(DatoLuz(0f)) }
        .collectAsStateWithLifecycle(initialValue = null)

    Scaffold(
        topBar = {
            Column {
                BannerConectividad(repositorioRed)
                TopAppBar(
                    title = { Text("Dashboard Hardware", fontWeight = FontWeight.Bold) }
                )
            }
        }
    ) { padding ->
        LazyColumn(
            modifier        = Modifier.padding(padding).fillMaxSize(),
            contentPadding  = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Red
            item {
                ElevatedCard(Modifier.fillMaxWidth()) {
                    Row(
                        Modifier.padding(16.dp),
                        verticalAlignment     = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Icon(
                            when (estadoRed) {
                                EstadoRed.CONECTADO_WIFI  -> Icons.Default.Wifi
                                EstadoRed.CONECTADO_DATOS -> Icons.Default.SignalCellularAlt
                                EstadoRed.SIN_CONEXION    -> Icons.Default.WifiOff
                            },
                            contentDescription = null,
                            tint = when (estadoRed) {
                                EstadoRed.SIN_CONEXION -> MaterialTheme.colorScheme.error
                                else                   -> MaterialTheme.colorScheme.primary
                            },
                            modifier = Modifier.size(36.dp)
                        )
                        Column {
                            Text("Red", fontWeight = FontWeight.SemiBold)
                            Text(
                                when (estadoRed) {
                                    EstadoRed.CONECTADO_WIFI  -> "WiFi conectado"
                                    EstadoRed.CONECTADO_DATOS -> "Datos móviles"
                                    EstadoRed.SIN_CONEXION    -> "Sin conexión"
                                },
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }

            // Movimiento (acelerómetro simplificado)
            item {
                ElevatedCard(Modifier.fillMaxWidth()) {
                    Column(Modifier.padding(16.dp)) {
                        Text("Movimiento", fontWeight = FontWeight.SemiBold)
                        Spacer(Modifier.height(8.dp))
                        acelerometro?.let { a ->
                            val magnitud = a.magnitud
                            val descripcion = when {
                                magnitud < 1  -> "Quieto"
                                magnitud < 5  -> "Ligero movimiento"
                                magnitud < 15 -> "En movimiento"
                                else          -> "Movimiento intenso"
                            }
                            Text(descripcion, color = MaterialTheme.colorScheme.primary,
                                style = MaterialTheme.typography.titleMedium)
                            Text("${"%.2f".format(magnitud)} m/s²",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant)
                        } ?: Text("Cargando...",
                            color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            }

            // Luz
            item {
                ElevatedCard(Modifier.fillMaxWidth()) {
                    Column(Modifier.padding(16.dp)) {
                        Text("Luz ambiente", fontWeight = FontWeight.SemiBold)
                        Spacer(Modifier.height(8.dp))
                        luz?.let { l ->
                            Text(l.descripcion, color = MaterialTheme.colorScheme.primary,
                                style = MaterialTheme.typography.titleMedium)
                            Text("${"%.1f".format(l.lux)} lux",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Spacer(Modifier.height(6.dp))
                            LinearProgressIndicator(
                                progress = { (l.lux / 100_000f).coerceIn(0f, 1f) },
                                modifier = Modifier.fillMaxWidth()
                            )
                        } ?: Text("Cargando...",
                            color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            }
        }
    }
}