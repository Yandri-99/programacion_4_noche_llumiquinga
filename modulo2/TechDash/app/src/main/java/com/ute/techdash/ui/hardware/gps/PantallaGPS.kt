package com.ute.techdash.ui.hardware.gps

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteSweep
import androidx.compose.material.icons.filled.LocationOff
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ute.techdash.utils.PermisosHelper

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaGPS() {
    val context = LocalContext.current
    val vm: UbicacionViewModel = viewModel(factory = UbicacionViewModel.factory(context))
    val state   by vm.state.collectAsStateWithLifecycle()

    // Verificar permiso de ubicación
    var tienePermiso by remember {
        mutableStateOf(
            PermisosHelper.tienePermiso(
                context, android.Manifest.permission.ACCESS_FINE_LOCATION
            )
        )
    }

    val lanzadorPermiso = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { resultados ->
        tienePermiso = resultados[android.Manifest.permission.ACCESS_FINE_LOCATION] == true ||
                resultados[android.Manifest.permission.ACCESS_COARSE_LOCATION] == true
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("GPS y Ubicación", fontWeight = FontWeight.Bold) },
                actions = {
                    if (state.historial.isNotEmpty()) {
                        IconButton(onClick = { vm.limpiarHistorial() }) {
                            Icon(Icons.Default.DeleteSweep, "Limpiar historial")
                        }
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier            = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Sin permiso
            if (!tienePermiso) {
                Card(
                    colors   = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer
                    )
                ) {
                    Column(Modifier.padding(16.dp)) {
                        Text(
                            "Permiso de ubicación requerido",
                            fontWeight = FontWeight.SemiBold,
                            color      = MaterialTheme.colorScheme.onErrorContainer
                        )
                        Spacer(Modifier.height(8.dp))
                        Button(onClick = {
                            lanzadorPermiso.launch(arrayOf(
                                android.Manifest.permission.ACCESS_FINE_LOCATION,
                                android.Manifest.permission.ACCESS_COARSE_LOCATION
                            ))
                        }) { Text("Conceder permiso") }
                    }
                }
                return@Column
            }

            // Tarjeta de ubicación actual
            ElevatedCard(Modifier.fillMaxWidth()) {
                Column(Modifier.padding(16.dp)) {
                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment     = Alignment.CenterVertically
                    ) {
                        Text("Ubicación actual", style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold)
                        Icon(
                            if (state.rastreando) Icons.Default.LocationOn
                            else                  Icons.Default.LocationOff,
                            contentDescription = null,
                            tint = if (state.rastreando) MaterialTheme.colorScheme.primary
                            else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Spacer(Modifier.height(12.dp))

                    if (state.ubicacion != null) {
                        val ub = state.ubicacion!!
                        InfoRow("Coordenadas", ub.coordenadas)
                        InfoRow("Precisión",   ub.precisionTexto)
                        InfoRow("Altitud",     "${"%.1f".format(ub.altitud)}m")
                        InfoRow("Velocidad",   "${"%.1f".format(ub.velocidad * 3.6)} km/h")
                    } else {
                        Text(
                            if (state.rastreando) "Obteniendo ubicación..."
                            else                  "Sin datos de ubicación",
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            // Error
            state.error?.let { msg ->
                Card(colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.errorContainer)
                ) {
                    Text(msg, Modifier.padding(12.dp),
                        color = MaterialTheme.colorScheme.onErrorContainer)
                }
            }

            // Botón de rastreo
            Button(
                onClick  = {
                    if (state.rastreando) vm.detenerRastreo()
                    else                  vm.iniciarRastreo()
                },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                colors   = if (state.rastreando)
                    ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                else
                    ButtonDefaults.buttonColors()
            ) {
                Icon(
                    if (state.rastreando) Icons.Default.Stop else Icons.Default.PlayArrow,
                    null
                )
                Spacer(Modifier.width(8.dp))
                Text(if (state.rastreando) "Detener rastreo" else "Iniciar rastreo")
            }

            // Historial
            if (state.historial.isNotEmpty()) {
                Text(
                    "Historial (${state.historial.size} puntos)",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold
                )
                LazyColumn(
                    modifier            = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    items(state.historial.reversed()) { ub ->
                        Card(Modifier.fillMaxWidth()) {
                            Row(
                                Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(ub.coordenadas,
                                    style = MaterialTheme.typography.bodySmall)
                                Text(ub.precisionTexto,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.primary)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun InfoRow(etiqueta: String, valor: String) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(vertical = 3.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(etiqueta, color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.bodySmall)
        Text(valor, fontWeight = FontWeight.Medium,
            style = MaterialTheme.typography.bodySmall)
    }
}