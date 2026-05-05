package com.ute.compose.material

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ute.compose.model.Contacto
import com.ute.compose.model.contactosDeMuestra

// ── Composable reutilizable — se usará desde el Paso 3 en adelante ───────────
@Composable
fun TarjetaContacto(
    contacto:  Contacto,
    onClick:   () -> Unit = {},
    onLlamar:  () -> Unit = {},
    onFavorito: () -> Unit = {}
) {
    // ElevatedCard con onClick → toda la tarjeta es presionable
    ElevatedCard(
        onClick  = onClick,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier          = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // ── Avatar con inicial ─────────────────────────────────────────
            // Box circular con clip(CircleShape) — patrón visto en página 11
            Box(
                modifier         = Modifier
                    .size(52.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text       = contacto.nombre.first().uppercase(),
                    style      = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color      = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }

            Spacer(Modifier.width(12.dp))

            // ── Datos del contacto ─────────────────────────────────────────
            // weight(1f) → esta Column toma el espacio sobrante del Row
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text       = contacto.nombre,
                    style      = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text  = contacto.email,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(Modifier.height(4.dp))
                // AssistChip — etiqueta pequeña no interactiva para información extra
                AssistChip(
                    onClick = {},
                    label   = { Text(contacto.telefono,
                        style = MaterialTheme.typography.labelSmall) }
                )
            }

            // ── Acciones rápidas ───────────────────────────────────────────
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                IconButton(onClick = onFavorito) {
                    Icon(
                        imageVector        = if (contacto.favorito) Icons.Default.Favorite
                        else Icons.Default.FavoriteBorder,
                        contentDescription = if (contacto.favorito) "Quitar favorito"
                        else "Marcar favorito",
                        tint               = if (contacto.favorito)
                            MaterialTheme.colorScheme.error
                        else
                            MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                IconButton(onClick = onLlamar) {
                    Icon(
                        imageVector        = Icons.Default.Phone,
                        contentDescription = "Llamar",
                        tint               = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}

// ── Screen del paso 2: muestra las tarjetas con datos estáticos ──────────────
@Composable
fun Paso02CardScreen() {
    Column(
        modifier            = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Paso 2 · Card y ElevatedCard",
            style = MaterialTheme.typography.titleMedium)
        HorizontalDivider()

        Text("ElevatedCard — ítem interactivo",
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.primary)

        // Mostramos los primeros 3 contactos de la lista de muestra
        contactosDeMuestra.take(3).forEach { contacto ->
            TarjetaContacto(
                contacto  = contacto,
                onClick   = { /* En el Paso 6: navegar al detalle */ },
                onLlamar  = { /* En el Paso 6: mostrar snackbar */ },
                onFavorito = { /* En el Paso 3: toggle en la lista */ }
            )
        }

        HorizontalDivider()
        Text("Comparación de variantes",
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.primary)

        // Card plana
        Card(modifier = Modifier.fillMaxWidth()) {
            Text("Card — sin elevación visible",
                Modifier.padding(16.dp))
        }

        // ElevatedCard
        ElevatedCard(modifier = Modifier.fillMaxWidth()) {
            Text("ElevatedCard — con sombra",
                Modifier.padding(16.dp))
        }

        // OutlinedCard
        OutlinedCard(modifier = Modifier.fillMaxWidth()) {
            Text("OutlinedCard — solo borde",
                Modifier.padding(16.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Paso02Preview() {
    MaterialTheme { Paso02CardScreen() }
}