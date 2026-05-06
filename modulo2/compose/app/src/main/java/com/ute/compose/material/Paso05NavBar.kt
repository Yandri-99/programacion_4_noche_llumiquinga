package com.ute.compose.material

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ute.compose.model.Contacto
import com.ute.compose.model.contactosDeMuestra

// Modelo del destino de navegación
// Separar datos de presentación es buena práctica (SRP)
data class DestinoNav(
    val ruta:          String,
    val etiqueta:      String,
    val iconoActivo:   ImageVector,
    val iconoInactivo: ImageVector
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Paso05NavBarScreen() {
    var destinoActual by remember { mutableStateOf("contactos") }
    var contactos     by remember { mutableStateOf(contactosDeMuestra) }

    val destinos = listOf(
        DestinoNav("contactos", "Contactos", Icons.Filled.People,       Icons.Outlined.People),
        DestinoNav("favoritos", "Favoritos", Icons.Filled.Favorite,     Icons.Outlined.FavoriteBorder),
        DestinoNav("perfil",    "Perfil",    Icons.Filled.AccountCircle, Icons.Outlined.AccountCircle),
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Agenda", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor    = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        },

        // ── NavigationBar ──────────────────────────────────────────────────
        bottomBar = {
            NavigationBar {
                destinos.forEach { destino ->
                    val seleccionado = destinoActual == destino.ruta
                    NavigationBarItem(
                        selected = seleccionado,
                        onClick  = { destinoActual = destino.ruta },
                        icon     = {
                            // Convención M3: relleno = activo, contorno = inactivo
                            Icon(
                                imageVector        = if (seleccionado) destino.iconoActivo
                                else destino.iconoInactivo,
                                contentDescription = destino.etiqueta
                            )
                        },
                        label = { Text(destino.etiqueta) }
                    )
                }
            }
        },

        floatingActionButton = {
            // FAB solo visible en la pestaña de contactos
            if (destinoActual == "contactos") {
                FloatingActionButton(onClick = { /* Paso 6 */ }) {
                    Icon(Icons.Default.PersonAdd, "Nuevo contacto")
                }
            }
        }

    ) { paddingValues ->
        // when actúa como router simple — en pág. 13 usaremos NavHost
        when (destinoActual) {
            "contactos" -> PantallaContactosContent(
                contactos  = contactos,
                onFavorito = { id ->
                    contactos = contactos.map { c ->
                        if (c.id == id) c.copy(favorito = !c.favorito) else c
                    }
                },
                modifier   = Modifier.padding(paddingValues)
            )
            "favoritos" -> PantallaFavoritosContent(
                favoritos = contactos.filter { it.favorito },
                modifier  = Modifier.padding(paddingValues)
            )
            "perfil"    -> PantallaPerfilContent(
                modifier  = Modifier.padding(paddingValues)
            )
        }
    }
}

// ── Contenido de cada pestaña ────────────────────────────────────────────────

@Composable
private fun PantallaContactosContent(
    contactos:  List<Contacto>,
    onFavorito: (Int) -> Unit,
    modifier:   Modifier = Modifier
) {
    LazyColumn(
        modifier            = modifier,
        contentPadding      = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(contactos, key = { it.id }) { contacto ->
            TarjetaContacto(
                contacto   = contacto,
                onFavorito = { onFavorito(contacto.id) }
            )
        }
        item { Spacer(Modifier.height(80.dp)) }
    }
}

@Composable
fun PantallaFavoritosContent(
    favoritos: List<Contacto>,
    modifier:  Modifier = Modifier
) {
    if (favoritos.isEmpty()) {
        Box(modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(Icons.Default.FavoriteBorder, null,
                    Modifier.size(56.dp), tint = MaterialTheme.colorScheme.onSurfaceVariant)
                Spacer(Modifier.height(12.dp))
                Text("Sin favoritos aún",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text("Toca el corazón en un contacto",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    } else {
        LazyColumn(
            modifier            = modifier,
            contentPadding      = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(favoritos, key = { it.id }) { contacto ->
                TarjetaContacto(contacto = contacto)
            }
        }
    }
}

@Composable
fun PantallaPerfilContent(modifier: Modifier = Modifier) {
    Box(modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(Icons.Default.AccountCircle, null, Modifier.size(80.dp),
                tint = MaterialTheme.colorScheme.primary)
            Spacer(Modifier.height(12.dp))
            Text("Mi Perfil", style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold)
            Text("Próximamente...",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Paso05_Preview() {
    MaterialTheme { Paso05NavBarScreen() }
}