package com.ute.compose.material

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ute.compose.model.Contacto
import com.ute.compose.model.contactosDeMuestra

@Composable
fun Paso03LazyColumnScreen() {
    // Estado mutable de la lista — usamos mutableStateOf con una lista
    // Al reasignar la lista, Compose detecta el cambio y recompone
    var contactos by remember { mutableStateOf(contactosDeMuestra) }
    var busqueda  by remember { mutableStateOf("") }
    var filtro    by remember { mutableStateOf("Todos") }

    // Derivamos la lista filtrada — se recalcula en cada recomposición
    val contactosFiltrados = contactos
        .filter { c ->
            when (filtro) {
                "Favoritos" -> c.favorito
                else        -> true
            }
        }
        .filter { c ->
            busqueda.isBlank() ||
                    c.nombre.contains(busqueda, ignoreCase = true) ||
                    c.email.contains(busqueda, ignoreCase = true)
        }

    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            "Paso 3 · LazyColumn + LazyRow",
            style    = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(16.dp)
        )

        // ── Campo de búsqueda (del Paso 1) ───────────────────────────────
        OutlinedTextField(
            value         = busqueda,
            onValueChange = { busqueda = it },
            placeholder   = { Text("Buscar...") },
            leadingIcon   = { Icon(Icons.Default.Search, contentDescription = null) },
            trailingIcon  = {
                if (busqueda.isNotEmpty())
                    IconButton(onClick = { busqueda = "" }) {
                        Icon(Icons.Default.Clear, contentDescription = "Limpiar")
                    }
            },
            singleLine = true,
            modifier   = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
        )

        Spacer(Modifier.height(8.dp))

        // ── LazyRow: filtros de categoría ────────────────────────────────
        // LazyRow = Row virtualizado → ideal para chips horizontales con scroll
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding        = PaddingValues(horizontal = 16.dp)
        ) {
            items(listOf("Todos", "Favoritos")) { opcion ->
                FilterChip(
                    selected = filtro == opcion,
                    onClick  = { filtro = opcion },
                    label    = { Text(opcion) },
                    // leadingIcon cambia según si está seleccionado
                    leadingIcon = if (filtro == opcion) {{
                        Icon(Icons.Default.Check,
                            contentDescription = null,
                            modifier = Modifier.size(FilterChipDefaults.IconSize))
                    }} else null
                )
            }
        }

        Spacer(Modifier.height(8.dp))

        // ── LazyColumn: lista principal ──────────────────────────────────
        if (contactosFiltrados.isEmpty()) {
            // Estado vacío — buena práctica siempre manejar lista vacía
            Box(
                modifier         = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        Icons.Default.SearchOff,
                        contentDescription = null,
                        modifier = Modifier.size(56.dp),
                        tint     = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(Modifier.height(12.dp))
                    Text("Sin resultados",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        } else {
            LazyColumn(
                // contentPadding: espacio en los bordes de la lista
                // No confundir con padding del Modifier — este respeta el scroll
                contentPadding      = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // item { } → elemento único (cabecera, separador, etc.)
                item {
                    Text(
                        "${contactosFiltrados.size} contacto(s)",
                        style    = MaterialTheme.typography.labelSmall,
                        color    = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                }

                // items(lista, key) → renderiza un composable por cada elemento
                // key = { it.id } → Compose identifica cada ítem por su id,
                // no por su posición — crítico para animaciones y rendimiento
                items(
                    items = contactosFiltrados,
                    key   = { it.id }
                ) { contacto ->
                    TarjetaContacto(
                        contacto  = contacto,
                        onLlamar  = { /* Paso 6: snackbar */ },
                        onFavorito = {
                            // Creamos nueva lista con el favorito modificado
                            // Las listas en Kotlin son inmutables por defecto —
                            // reasignamos en lugar de mutar
                            contactos = contactos.map { c ->
                                if (c.id == contacto.id) c.copy(favorito = !c.favorito)
                                else c
                            }
                        }
                    )
                }

                // item de pie — espacio para futura NavigationBar
                item { Spacer(Modifier.height(16.dp)) }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Paso03_Preview() {
    MaterialTheme { Paso03LazyColumnScreen() }
}