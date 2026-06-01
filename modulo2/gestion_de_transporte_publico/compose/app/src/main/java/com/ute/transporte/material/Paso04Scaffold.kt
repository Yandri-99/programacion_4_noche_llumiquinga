package com.ute.transporte.material

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ute.transporte.model.paradasDeMuestra

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Paso04ScaffoldScreen() {
    var paradas  by remember { mutableStateOf(paradasDeMuestra) }
    var busqueda   by remember { mutableStateOf("") }
    var filtro     by remember { mutableStateOf("Todas") }
    var mostrarFab by remember { mutableStateOf(false) }

    val paradasFiltradas = paradas
        .filter { p -> if (filtro == "Favoritas") p.favorito else true }
        .filter { p ->
            busqueda.isBlank() ||
                    p.nombre.contains(busqueda, ignoreCase = true)
        }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Paradas (${paradas.size})",
                        fontWeight = FontWeight.Bold
                    )
                },
                actions = {
                    IconButton(onClick = {
                        filtro = if (filtro == "Favoritas") "Todas" else "Favoritas"
                    }) {
                        Icon(
                            imageVector        = if (filtro == "Favoritas")
                                Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = "Filtrar favoritas",
                            tint               = if (filtro == "Favoritas")
                                MaterialTheme.colorScheme.error
                            else
                                MaterialTheme.colorScheme.onSurface
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        },

        floatingActionButton = {
            FloatingActionButton(
                onClick = { mostrarFab = true }
            ) {
                Icon(Icons.Default.Add, contentDescription = "Nueva parada")
            }
        }

    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            OutlinedTextField(
                value         = busqueda,
                onValueChange = { busqueda = it },
                placeholder   = { Text("Buscar parada...") },
                leadingIcon   = { Icon(Icons.Default.Search, null) },
                trailingIcon  = {
                    if (busqueda.isNotEmpty())
                        IconButton(onClick = { busqueda = "" }) {
                            Icon(Icons.Default.Clear, "Limpiar")
                        }
                },
                singleLine = true,
                modifier   = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            )

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding        = PaddingValues(horizontal = 16.dp)
            ) {
                items(listOf("Todas", "Favoritas")) { opcion ->
                    FilterChip(
                        selected = filtro == opcion,
                        onClick  = { filtro = opcion },
                        label    = { Text(opcion) },
                        leadingIcon = if (filtro == opcion) {{
                            Icon(Icons.Default.Check, null,
                                Modifier.size(FilterChipDefaults.IconSize))
                        }} else null
                    )
                }
            }

            Spacer(Modifier.height(4.dp))

            LazyColumn(
                contentPadding      = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    Text("${paradasFiltradas.size} resultado(s)",
                        style    = MaterialTheme.typography.labelSmall,
                        color    = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(bottom = 4.dp))
                }
                items(paradasFiltradas, key = { it.id }) { parada ->
                    TarjetaParada(
                        parada  = parada,
                        onFavorito = {
                            paradas = paradas.map { p ->
                                if (p.id == parada.id) p.copy(favorito = !p.favorito) else p
                            }
                        }
                    )
                }
                item { Spacer(Modifier.height(80.dp)) }
            }
        }
    }

    if (mostrarFab) {
        AlertDialog(
            onDismissRequest = { mostrarFab = false },
            title   = { Text("Nueva parada") },
            text    = { Text("Función para agregar una nueva parada de bus.") },
            confirmButton = {
                TextButton(onClick = { mostrarFab = false }) { Text("OK") }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun Paso04_Preview() {
    MaterialTheme { Paso04ScaffoldScreen() }
}
