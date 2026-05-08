package com.ute.compose.ui.theme.viewmodel

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ute.compose.viewmodel.ProductosUiStateViewModel
import com.ute.compose.viewmodel.UiState

@Composable
fun Paso02_UiStateScreen(vm: ProductosUiStateViewModel = viewModel()) {
    val uiState  by vm.uiState.collectAsStateWithLifecycle()
    val busqueda by vm.busqueda.collectAsStateWithLifecycle()

    // Estado local solo para el botón de simulación de error
    var simularError by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            "Paso 2 · sealed class UiState",
            style    = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(16.dp)
        )

        // Botones para probar los tres estados manualmente
        Row(
            modifier              = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedButton(
                onClick  = { simularError = false; vm.recargar() },
                modifier = Modifier.weight(1f)
            ) { Text("✓ Éxito") }

            OutlinedButton(
                onClick  = { simularError = true; vm.cargarProductos(simularError = true) },
                modifier = Modifier.weight(1f),
                colors   = ButtonDefaults.outlinedButtonColors(
                    contentColor = MaterialTheme.colorScheme.error
                )
            ) { Text("✗ Error") }
        }

        Spacer(Modifier.height(8.dp))

        OutlinedTextField(
            value         = busqueda,
            onValueChange = { vm.actualizarBusqueda(it) },
            placeholder   = { Text("Buscar...") },
            leadingIcon   = { Icon(Icons.Default.Search, null) },
            trailingIcon  = {
                if (busqueda.isNotEmpty())
                    IconButton(onClick = { vm.actualizarBusqueda("") }) {
                        Icon(Icons.Default.Clear, "Limpiar")
                    }
            },
            singleLine = true,
            modifier   = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )

        Spacer(Modifier.height(8.dp))

        // when sobre sealed class — el compilador garantiza exhaustividad
        when (val estado = uiState) {

            // ── Loading ────────────────────────────────────────────────────
            is UiState.Loading -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        CircularProgressIndicator()
                        Spacer(Modifier.height(12.dp))
                        Text("Cargando productos...",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            }

            // ── Error ──────────────────────────────────────────────────────
            is UiState.Error -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        modifier            = Modifier.padding(32.dp)
                    ) {
                        Icon(Icons.Default.WifiOff, null,
                            Modifier.size(56.dp),
                            tint = MaterialTheme.colorScheme.error)
                        Text(
                            text  = estado.message,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.error
                        )
                        Button(onClick = { vm.recargar() }) {
                            Icon(Icons.Default.Refresh, null)
                            Spacer(Modifier.width(8.dp))
                            Text("Reintentar")
                        }
                    }
                }
            }

            // ── Success ────────────────────────────────────────────────────
            is UiState.Success -> {
                Text(
                    "${estado.data.size} producto(s)",
                    style    = MaterialTheme.typography.labelSmall,
                    color    = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
                )
                LazyColumn(
                    contentPadding      = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(estado.data, key = { it.id }) { producto ->
                        TarjetaProductoSimple(producto = producto)
                    }
                }
            }
        }
    }
}

@Composable
fun TarjetaProductoSimple(producto: com.ute.compose.model.Producto) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier          = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text  = producto.nombre,
                    style = MaterialTheme.typography.titleSmall
                )
                Text(
                    text  = "${producto.precio} $",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            Badge(
                containerColor = if (producto.stock > 0) 
                    MaterialTheme.colorScheme.primaryContainer 
                else 
                    MaterialTheme.colorScheme.errorContainer
            ) {
                Text("Stock: ${producto.stock}")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Paso02Preview() {
    MaterialTheme { Paso02_UiStateScreen() }
}
