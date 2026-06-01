package com.ute.transporte.material

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.ute.transporte.model.Parada
import com.ute.transporte.model.paradasDeMuestra
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.draw.clip

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Paso06DialogosScreen() {
    var paradas        by remember { mutableStateOf(paradasDeMuestra) }
    var busqueda         by remember { mutableStateOf("") }
    var filtro           by remember { mutableStateOf("Todas") }
    var destinoActual    by remember { mutableStateOf("paradas") }

    var mostrarNuevo     by remember { mutableStateOf(false) }
    var paradaAEliminar by remember { mutableStateOf<Parada?>(null) }

    var mensajeSnack     by remember { mutableStateOf<String?>(null) }
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(mensajeSnack) {
        mensajeSnack?.let {
            snackbarHostState.showSnackbar(it)
            mensajeSnack = null
        }
    }

    val paradasFiltradas = paradas
        .filter { p -> if (filtro == "Favoritas") p.favorito else true }
        .filter { p -> busqueda.isBlank() || p.nombre.contains(busqueda, ignoreCase = true) }

    val destinos = listOf(
        DestinoNav("paradas", "Paradas", Icons.Filled.DirectionsBus,       Icons.Outlined.DirectionsBus),
        DestinoNav("favoritas", "Favoritas", Icons.Filled.Favorite,     Icons.Outlined.FavoriteBorder),
        DestinoNav("perfil",    "Perfil",    Icons.Filled.AccountCircle, Icons.Outlined.AccountCircle),
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Transporte (${paradas.size})", fontWeight = FontWeight.Bold)
                },
                actions = {
                    IconButton(onClick = {
                        filtro = if (filtro == "Favoritas") "Todas" else "Favoritas"
                    }) {
                        Icon(
                            imageVector = if (filtro == "Favoritas")
                                Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = "Filtrar favoritas",
                            tint = if (filtro == "Favoritas")
                                MaterialTheme.colorScheme.error
                            else
                                MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor    = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        },
        bottomBar = {
            NavigationBar {
                destinos.forEach { destino ->
                    val sel = destinoActual == destino.ruta
                    NavigationBarItem(
                        selected = sel,
                        onClick  = { destinoActual = destino.ruta },
                        icon     = {
                            Icon(if (sel) destino.iconoActivo else destino.iconoInactivo,
                                destino.etiqueta)
                        },
                        label = { Text(destino.etiqueta) }
                    )
                }
            }
        },
        floatingActionButton = {
            if (destinoActual == "paradas") {
                FloatingActionButton(onClick = { mostrarNuevo = true }) {
                    Icon(Icons.Default.Add, "Nueva parada")
                }
            }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }

    ) { paddingValues ->
        when (destinoActual) {
            "paradas" -> ContenidoParadas(
                paradas    = paradasFiltradas,
                busqueda     = busqueda,
                filtro       = filtro,
                onBusqueda   = { busqueda = it },
                onFiltro     = { filtro = it },
                onFavorito   = { id ->
                    paradas = paradas.map { p ->
                        if (p.id == id) p.copy(favorito = !p.favorito) else p
                    }
                },
                onLlamar     = { nombre -> mensajeSnack = "📞 Llamando a $nombre..." },
                onEliminar   = { parada -> paradaAEliminar = parada },
                modifier     = Modifier.padding(paddingValues)
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

    if (mostrarNuevo) {
        DialogNuevaParada(
            onDismiss = { mostrarNuevo = false },
            onGuardar = { nuevo ->
                paradas    = paradas + nuevo
                mostrarNuevo = false
                mensajeSnack = "✅ ${nuevo.nombre} agregada"
            }
        )
    }

    paradaAEliminar?.let { parada ->
        AlertDialog(
            onDismissRequest = { paradaAEliminar = null },
            icon    = {
                Icon(Icons.Default.Warning, null,
                    tint = MaterialTheme.colorScheme.error)
            },
            title   = { Text("Eliminar parada") },
            text    = {
                Text("¿Seguro que quieres eliminar ${parada.nombre}? " +
                        "Esta acción no se puede deshacer.")
            },
            confirmButton = {
                Button(
                    onClick = {
                        paradas         = paradas.filter { it.id != parada.id }
                        mensajeSnack      = "🗑 ${parada.nombre} eliminada"
                        paradaAEliminar = null
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error
                    )
                ) { Text("Eliminar") }
            },
            dismissButton = {
                OutlinedButton(onClick = { paradaAEliminar = null }) {
                    Text("Cancelar")
                }
            }
        )
    }
}

@Composable
private fun ContenidoParadas(
    paradas:  List<Parada>,
    busqueda:   String,
    filtro:     String,
    onBusqueda: (String) -> Unit,
    onFiltro:   (String) -> Unit,
    onFavorito: (Int) -> Unit,
    onLlamar:   (String) -> Unit,
    onEliminar: (Parada) -> Unit,
    modifier:   Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize()) {
        OutlinedTextField(
            value         = busqueda,
            onValueChange = onBusqueda,
            placeholder   = { Text("Buscar parada...") },
            leadingIcon   = { Icon(Icons.Default.Search, null) },
            trailingIcon  = {
                if (busqueda.isNotEmpty())
                    IconButton(onClick = { onBusqueda("") }) {
                        Icon(Icons.Default.Clear, "Limpiar")
                    }
            },
            singleLine = true,
            modifier   = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp)
        )

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding        = PaddingValues(horizontal = 16.dp)
        ) {
            items(listOf("Todas", "Favoritas")) { opcion ->
                FilterChip(
                    selected    = filtro == opcion,
                    onClick     = { onFiltro(opcion) },
                    label       = { Text(opcion) },
                    leadingIcon = if (filtro == opcion) {{
                        Icon(Icons.Default.Check, null,
                            Modifier.size(FilterChipDefaults.IconSize))
                    }} else null
                )
            }
        }

        Spacer(Modifier.height(4.dp))

        if (paradas.isEmpty()) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Default.SearchOff, null, Modifier.size(56.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant)
                    Spacer(Modifier.height(8.dp))
                    Text("Sin resultados",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        } else {
            LazyColumn(
                contentPadding      = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    Text("${paradas.size} parada(s)",
                        style    = MaterialTheme.typography.labelSmall,
                        color    = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(bottom = 4.dp))
                }
                items(paradas, key = { it.id }) { parada ->
                    TarjetaParadaCompleta(
                        parada   = parada,
                        onFavorito = { onFavorito(parada.id) },
                        onLlamar   = { onLlamar(parada.nombre) },
                        onEliminar = { onEliminar(parada) }
                    )
                }
                item { Spacer(Modifier.height(100.dp)) }
            }
        }
    }
}

@Composable
private fun TarjetaParadaCompleta(
    parada:  Parada,
    onFavorito: () -> Unit,
    onLlamar:  () -> Unit,
    onEliminar: () -> Unit
) {
    ElevatedCard(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier          = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                Modifier
                    .size(52.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primaryContainer)
                , contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.DirectionsBus,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
            Spacer(Modifier.width(12.dp))
            Column(Modifier.weight(1f)) {
                Text(parada.nombre, fontWeight = FontWeight.SemiBold,
                    style = MaterialTheme.typography.titleSmall)
                Text(parada.direccion,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text("Ruta ${parada.ruta}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            IconButton(onClick = onFavorito) {
                Icon(
                    if (parada.favorito) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    null,
                    tint = if (parada.favorito) MaterialTheme.colorScheme.error
                    else MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            IconButton(onClick = onLlamar) {
                Icon(Icons.Default.Phone, null,
                    tint = MaterialTheme.colorScheme.primary)
            }
            IconButton(onClick = onEliminar) {
                Icon(Icons.Default.Delete, null,
                    tint = MaterialTheme.colorScheme.error)
            }
        }
    }
}

@Composable
private fun DialogNuevaParada(
    onDismiss: () -> Unit,
    onGuardar: (Parada) -> Unit
) {
    var nombre   by remember { mutableStateOf("") }
    var direccion by remember { mutableStateOf("") }
    var ruta by remember { mutableStateOf("") }

    val nombreValido   = nombre.trim().length >= 2
    val direccionValido    = direccion.trim().length >= 3
    val rutaValido = ruta.trim().length >= 1
    val valido         = nombreValido && direccionValido && rutaValido

    Dialog(onDismissRequest = onDismiss) {
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier            = Modifier.padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text("Nueva parada",
                    style      = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold)

                OutlinedTextField(
                    value           = nombre,
                    onValueChange   = { nombre = it },
                    label           = { Text("Nombre de parada") },
                    leadingIcon     = { Icon(Icons.Default.DirectionsBus, null) },
                    isError         = nombre.isNotEmpty() && !nombreValido,
                    singleLine      = true,
                    modifier        = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
                )

                OutlinedTextField(
                    value           = direccion,
                    onValueChange   = { direccion = it },
                    label           = { Text("Dirección") },
                    leadingIcon     = { Icon(Icons.Default.LocationOn, null) },
                    isError         = direccion.isNotEmpty() && !direccionValido,
                    singleLine      = true,
                    modifier        = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction    = ImeAction.Next
                    )
                )

                OutlinedTextField(
                    value           = ruta,
                    onValueChange   = { ruta = it },
                    label           = { Text("Ruta") },
                    leadingIcon     = { Icon(Icons.Default.Route, null) },
                    isError         = ruta.isNotEmpty() && !rutaValido,
                    singleLine      = true,
                    modifier        = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction    = ImeAction.Done
                    )
                )

                Row(
                    modifier              = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment     = Alignment.CenterVertically
                ) {
                    TextButton(onClick = onDismiss) { Text("Cancelar") }
                    Spacer(Modifier.width(8.dp))
                    Button(
                        onClick  = {
                            onGuardar(
                                Parada(
                                    id       = System.currentTimeMillis().toInt(),
                                    nombre   = nombre.trim(),
                                    direccion = direccion.trim(),
                                    ruta = ruta.trim()
                                )
                            )
                        },
                        enabled  = valido
                    ) { Text("Guardar") }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Paso06_Preview() {
    MaterialTheme { Paso06DialogosScreen() }
}
