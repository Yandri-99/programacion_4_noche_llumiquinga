package com.ute.compose.material


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
import com.ute.compose.model.Contacto
import com.ute.compose.model.contactosDeMuestra
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.draw.clip


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Paso06DialogosScreen() {
    var contactos        by remember { mutableStateOf(contactosDeMuestra) }
    var busqueda         by remember { mutableStateOf("") }
    var filtro           by remember { mutableStateOf("Todos") }
    var destinoActual    by remember { mutableStateOf("contactos") }


    // Estado de los diálogos — cada booleano controla visibilidad de un dialog
    var mostrarNuevo     by remember { mutableStateOf(false) }
    var contactoAEliminar by remember { mutableStateOf<Contacto?>(null) }


    // Snackbar: un String nullable — null = nada que mostrar
    var mensajeSnack     by remember { mutableStateOf<String?>(null) }
    val snackbarHostState = remember { SnackbarHostState() }


    // LaunchedEffect: ejecuta el bloque cada vez que 'mensajeSnack' cambia
    // Es una coroutine — showSnackbar() suspende hasta que el snackbar desaparece
    LaunchedEffect(mensajeSnack) {
        mensajeSnack?.let {
            snackbarHostState.showSnackbar(it)
            mensajeSnack = null
        }
    }


    val contactosFiltrados = contactos
        .filter { c -> if (filtro == "Favoritos") c.favorito else true }
        .filter { c -> busqueda.isBlank() || c.nombre.contains(busqueda, ignoreCase = true) }


    val destinos = listOf(
        DestinoNav("contactos", "Contactos", Icons.Filled.People,       Icons.Outlined.People),
        DestinoNav("favoritos", "Favoritos", Icons.Filled.Favorite,     Icons.Outlined.FavoriteBorder),
        DestinoNav("perfil",    "Perfil",    Icons.Filled.AccountCircle, Icons.Outlined.AccountCircle),
    )


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Agenda (${contactos.size})", fontWeight = FontWeight.Bold)
                },
                actions = {
                    IconButton(onClick = {
                        filtro = if (filtro == "Favoritos") "Todos" else "Favoritos"
                    }) {
                        Icon(
                            imageVector = if (filtro == "Favoritos")
                                Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = "Filtrar favoritos",
                            tint = if (filtro == "Favoritos")
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
            if (destinoActual == "contactos") {
                FloatingActionButton(onClick = { mostrarNuevo = true }) {
                    Icon(Icons.Default.PersonAdd, "Nuevo contacto")
                }
            }
        },
        // snackbarHost conecta el SnackbarHostState con el Scaffold
        snackbarHost = { SnackbarHost(snackbarHostState) }


    ) { paddingValues ->
        when (destinoActual) {
            "contactos" -> ContenidoContactos(
                contactos    = contactosFiltrados,
                busqueda     = busqueda,
                filtro       = filtro,
                onBusqueda   = { busqueda = it },
                onFiltro     = { filtro = it },
                onFavorito   = { id ->
                    contactos = contactos.map { c ->
                        if (c.id == id) c.copy(favorito = !c.favorito) else c
                    }
                },
                onLlamar     = { nombre -> mensajeSnack = "📞 Llamando a $nombre..." },
                onEliminar   = { contacto -> contactoAEliminar = contacto },
                modifier     = Modifier.padding(paddingValues)
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


    // ── Diálogo 1: Nuevo contacto (Dialog personalizado) ────────────────────
    // Usamos Dialog (no AlertDialog) para tener control total del contenido
    if (mostrarNuevo) {
        DialogNuevoContacto(
            onDismiss = { mostrarNuevo = false },
            onGuardar = { nuevo ->
                contactos    = contactos + nuevo
                mostrarNuevo = false
                mensajeSnack = "✅ ${nuevo.nombre} agregado"
            }
        )
    }


    // ── Diálogo 2: Confirmar eliminación (AlertDialog estándar) ─────────────
    // contactoAEliminar != null → mostrar dialog; null → ocultarlo
    contactoAEliminar?.let { contacto ->
        AlertDialog(
            onDismissRequest = { contactoAEliminar = null },
            icon    = {
                Icon(Icons.Default.Warning, null,
                    tint = MaterialTheme.colorScheme.error)
            },
            title   = { Text("Eliminar contacto") },
            text    = {
                Text("¿Seguro que quieres eliminar a ${contacto.nombre}? " +
                        "Esta acción no se puede deshacer.")
            },
            confirmButton = {
                Button(
                    onClick = {
                        contactos         = contactos.filter { it.id != contacto.id }
                        mensajeSnack      = "🗑 ${contacto.nombre} eliminado"
                        contactoAEliminar = null
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error
                    )
                ) { Text("Eliminar") }
            },
            dismissButton = {
                OutlinedButton(onClick = { contactoAEliminar = null }) {
                    Text("Cancelar")
                }
            }
        )
    }
}


// ── Contenido de la pestaña Contactos ───────────────────────────────────────


@Composable
private fun ContenidoContactos(
    contactos:  List<Contacto>,
    busqueda:   String,
    filtro:     String,
    onBusqueda: (String) -> Unit,
    onFiltro:   (String) -> Unit,
    onFavorito: (Int) -> Unit,
    onLlamar:   (String) -> Unit,
    onEliminar: (Contacto) -> Unit,
    modifier:   Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize()) {
        OutlinedTextField(
            value         = busqueda,
            onValueChange = onBusqueda,
            placeholder   = { Text("Buscar contacto...") },
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
            items(listOf("Todos", "Favoritos")) { opcion ->
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


        if (contactos.isEmpty()) {
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
                    Text("${contactos.size} contacto(s)",
                        style    = MaterialTheme.typography.labelSmall,
                        color    = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(bottom = 4.dp))
                }
                items(contactos, key = { it.id }) { contacto ->
                    // TarjetaContacto ahora con TODOS los callbacks conectados
                    TarjetaContactoCompleta(
                        contacto   = contacto,
                        onFavorito = { onFavorito(contacto.id) },
                        onLlamar   = { onLlamar(contacto.nombre) },
                        onEliminar = { onEliminar(contacto) }
                    )
                }
                item { Spacer(Modifier.height(100.dp)) }
            }
        }
    }
}


// TarjetaContacto extendida con botón de eliminar
@Composable
private fun TarjetaContactoCompleta(
    contacto:  Contacto,
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
                Text(
                    contacto.nombre.first().uppercase(),
                    style      = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color      = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
            Spacer(Modifier.width(12.dp))
            Column(Modifier.weight(1f)) {
                Text(contacto.nombre, fontWeight = FontWeight.SemiBold,
                    style = MaterialTheme.typography.titleSmall)
                Text(contacto.email,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text(contacto.telefono,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            IconButton(onClick = onFavorito) {
                Icon(
                    if (contacto.favorito) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    null,
                    tint = if (contacto.favorito) MaterialTheme.colorScheme.error
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


// ── Diálogo personalizado: formulario de nuevo contacto ──────────────────────
@Composable
private fun DialogNuevoContacto(
    onDismiss: () -> Unit,
    onGuardar: (Contacto) -> Unit
) {
    var nombre   by remember { mutableStateOf("") }
    var email    by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }


    val nombreValido   = nombre.trim().length >= 2
    val emailValido    = email.contains("@") && email.contains(".")
    val telefonoValido = telefono.length >= 7
    val valido         = nombreValido && emailValido && telefonoValido


    // Dialog (no AlertDialog) → contenido completamente personalizado
    Dialog(onDismissRequest = onDismiss) {
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier            = Modifier.padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text("Nuevo contacto",
                    style      = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold)


                OutlinedTextField(
                    value           = nombre,
                    onValueChange   = { nombre = it },
                    label           = { Text("Nombre") },
                    leadingIcon     = { Icon(Icons.Default.Person, null) },
                    isError         = nombre.isNotEmpty() && !nombreValido,
                    singleLine      = true,
                    modifier        = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
                )


                OutlinedTextField(
                    value           = email,
                    onValueChange   = { email = it },
                    label           = { Text("Email") },
                    leadingIcon     = { Icon(Icons.Default.Email, null) },
                    isError         = email.isNotEmpty() && !emailValido,
                    singleLine      = true,
                    modifier        = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction    = ImeAction.Next
                    )
                )


                OutlinedTextField(
                    value           = telefono,
                    onValueChange   = { telefono = it },
                    label           = { Text("Teléfono") },
                    leadingIcon     = { Icon(Icons.Default.Phone, null) },
                    isError         = telefono.isNotEmpty() && !telefonoValido,
                    singleLine      = true,
                    modifier        = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Phone,
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
                                Contacto(
                                    id       = System.currentTimeMillis().toInt(),
                                    nombre   = nombre.trim(),
                                    email    = email.trim(),
                                    telefono = telefono.trim()
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
