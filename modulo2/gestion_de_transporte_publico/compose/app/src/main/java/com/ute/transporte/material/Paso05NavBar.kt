package com.ute.transporte.material

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
import com.ute.transporte.model.Parada
import com.ute.transporte.model.paradasDeMuestra

data class DestinoNav(
    val ruta:          String,
    val etiqueta:      String,
    val iconoActivo:   ImageVector,
    val iconoInactivo: ImageVector
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Paso05NavBarScreen() {
    var destinoActual by remember { mutableStateOf("paradas") }
    var paradas     by remember { mutableStateOf(paradasDeMuestra) }

    val destinos = listOf(
        DestinoNav("paradas", "Paradas", Icons.Filled.DirectionsBus,       Icons.Outlined.DirectionsBus),
        DestinoNav("favoritas", "Favoritas", Icons.Filled.Favorite,     Icons.Outlined.FavoriteBorder),
        DestinoNav("perfil",    "Perfil",    Icons.Filled.AccountCircle, Icons.Outlined.AccountCircle),
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Transporte Público", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor    = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        },

        bottomBar = {
            NavigationBar {
                destinos.forEach { destino ->
                    val seleccionado = destinoActual == destino.ruta
                    NavigationBarItem(
                        selected = seleccionado,
                        onClick  = { destinoActual = destino.ruta },
                        icon     = {
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
            if (destinoActual == "paradas") {
                FloatingActionButton(onClick = { }) {
                    Icon(Icons.Default.Add, "Nueva parada")
                }
            }
        }

    ) { paddingValues ->
        when (destinoActual) {
            "paradas" -> PantallaParadasContent(
                paradas  = paradas,
                onFavorito = { id ->
                    paradas = paradas.map { p ->
                        if (p.id == id) p.copy(favorito = !p.favorito) else p
                    }
                },
                modifier   = Modifier.padding(paddingValues)
            )
            "favoritas" -> PantallaFavoritasContent(
                favoritas = paradas.filter { it.favorito },
                modifier  = Modifier.padding(paddingValues)
            )
            "perfil"    -> PantallaPerfilContent(
                modifier  = Modifier.padding(paddingValues)
            )
        }
    }
}

@Composable
private fun PantallaParadasContent(
    paradas:  List<Parada>,
    onFavorito: (Int) -> Unit,
    modifier:   Modifier = Modifier
) {
    LazyColumn(
        modifier            = modifier,
        contentPadding      = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(paradas, key = { it.id }) { parada ->
            TarjetaParada(
                parada   = parada,
                onFavorito = { onFavorito(parada.id) }
            )
        }
        item { Spacer(Modifier.height(80.dp)) }
    }
}

@Composable
fun PantallaFavoritasContent(
    favoritas: List<Parada>,
    modifier:  Modifier = Modifier
) {
    if (favoritas.isEmpty()) {
        Box(modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(Icons.Default.FavoriteBorder, null,
                    Modifier.size(56.dp), tint = MaterialTheme.colorScheme.onSurfaceVariant)
                Spacer(Modifier.height(12.dp))
                Text("Sin favoritas aún",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text("Toca el corazón en una parada",
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
            items(favoritas, key = { it.id }) { parada ->
                TarjetaParada(parada = parada)
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
