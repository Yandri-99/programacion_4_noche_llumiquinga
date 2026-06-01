package com.ute.transporte.material

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
import com.ute.transporte.model.Parada
import com.ute.transporte.model.paradasDeMuestra

@Composable
fun TarjetaParada(
    parada:  Parada,
    onClick:   () -> Unit = {},
    onLlamar:  () -> Unit = {},
    onFavorito: () -> Unit = {}
) {
    ElevatedCard(
        onClick  = onClick,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier          = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier         = Modifier
                    .size(52.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.DirectionsBus,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }

            Spacer(Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text       = parada.nombre,
                    style      = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text  = parada.direccion,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(Modifier.height(4.dp))
                AssistChip(
                    onClick = {},
                    label   = { Text("Ruta ${parada.ruta}",
                        style = MaterialTheme.typography.labelSmall) }
                )
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                IconButton(onClick = onFavorito) {
                    Icon(
                        imageVector        = if (parada.favorito) Icons.Default.Favorite
                        else Icons.Default.FavoriteBorder,
                        contentDescription = if (parada.favorito) "Quitar favorito"
                        else "Marcar favorito",
                        tint               = if (parada.favorito)
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

@Composable
fun Paso02CardScreen() {
    Column(
        modifier            = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Paso 2 · Card y ElevatedCard",
            style = MaterialTheme.typography.titleMedium)
        HorizontalDivider()

        Text("Paradas de transporte",
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.primary)

        paradasDeMuestra.take(3).forEach { parada ->
            TarjetaParada(
                parada  = parada,
                onClick   = { },
                onLlamar  = { },
                onFavorito = { }
            )
        }

        HorizontalDivider()
        Text("Comparación de variantes",
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.primary)

        Card(modifier = Modifier.fillMaxWidth()) {
            Text("Card — sin elevación visible",
                Modifier.padding(16.dp))
        }

        ElevatedCard(modifier = Modifier.fillMaxWidth()) {
            Text("ElevatedCard — con sombra",
                Modifier.padding(16.dp))
        }

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
