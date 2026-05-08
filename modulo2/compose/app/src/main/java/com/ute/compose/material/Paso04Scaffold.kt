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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ute.compose.model.contactosDeMuestra
import com.ute.compose.model.contactosDeMuestra
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Paso04ScaffoldScreen() {
    var contactos  by remember { mutableStateOf(contactosDeMuestra) }
    var busqueda   by remember { mutableStateOf("") }
    var filtro     by remember { mutableStateOf("Todos") }
    var mostrarFab by remember { mutableStateOf(false) } // feedback del FAB

    val contactosFiltrados = contactos
        .filter { c -> if (filtro == "Favoritos") c.favorito else true }
        .filter { c ->
            busqueda.isBlank() ||
                    c.nombre.contains(busqueda, ignoreCase = true)
        }

    Scaffold(
        // ── TopAppBar ──────────────────────────────────────────────────────
        topBar = {
            TopAppBar(
                title = {
                    // El título muestra el conteo — estado elevado al Scaffold
                    Text(
                        "Contactos (${contactos.size})",
                        fontWeight = FontWeight.Bold
                    )
                },
                // actions: iconos a la derecha del título
                actions = {
                    // Ícono de favoritos como acceso rápido
                    IconButton(onClick = {
                        filtro = if (filtro == "Favoritos") "Todos" else "Favoritos"
                    }) {
                        Icon(
                            imageVector        = if (filtro == "Favoritos")
                                Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = "Filtrar favoritos",
                            tint               = if (filtro == "Favoritos")
                                MaterialTheme.colorScheme.error
                            else
                                MaterialTheme.colorScheme.onSurface
                        )
                    }
                },
                // colors: personaliza los colores de la barra
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        },

        // ── FloatingActionButton ───────────────────────────────────────────
        floatingActionButton = {
            FloatingActionButton(
                onClick = { mostrarFab = true }
            ) {
                Icon(Icons.Default.PersonAdd, contentDescription = "Nuevo contacto")
            }
        }

        // ── Content ────────────────────────────────────────────────────────────
        // paddingValues contiene el espacio que ocupa topBar y bottomBar
        // SIEMPRE aplícalo al contenido — de lo contrario queda bajo la TopAppBar
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)  // ← CRÍTICO: aplica el padding del Scaffold
                .fillMaxSize()
        ) {
            // Búsqueda
            OutlinedTextField(
                value         = busqueda,
                onValueChange = { busqueda = it },
                placeholder   = { Text("Buscar contacto...") },
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

            // Chips de filtro
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding        = PaddingValues(horizontal = 16.dp)
            ) {
                items(listOf("Todos", "Favoritos")) { opcion ->
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

            // Lista
            LazyColumn(
                contentPadding      = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    Text("${contactosFiltrados.size} resultado(s)",
                        style    = MaterialTheme.typography.labelSmall,
                        color    = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(bottom = 4.dp))
                }
                items(contactosFiltrados, key = { it.id }) { contacto ->
                    TarjetaContacto(
                        contacto  = contacto,
                        onFavorito = {
                            contactos = contactos.map { c ->
                                if (c.id == contacto.id) c.copy(favorito = !c.favorito) else c
                            }
                        }
                    )
                }
                item { Spacer(Modifier.height(80.dp)) } // espacio para FAB
            }
        }
    }

    // Feedback temporal del FAB — en el Paso 6 se reemplaza por un diálogo real
    if (mostrarFab) {
        AlertDialog(
            onDismissRequest = { mostrarFab = false },
            title   = { Text("Nuevo contacto") },
            text    = { Text("Esta función se conectará con el formulario del Paso 1 en el Paso 6.") },
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