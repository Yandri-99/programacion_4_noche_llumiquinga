package com.ute.techdash.ui.multimedia

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PermMedia
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material.icons.filled.VideoLibrary
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.ute.techdash.utils.PermisosHelper

// Llamamos ItemMedia (no MediaItem) para evitar conflicto de nombre con
// androidx.media3.common.MediaItem que se importa en ReproductorVideo.kt
sealed class ItemMedia {
    data class Imagen(val uri: Uri) : ItemMedia()
    data class Video(val uri: Uri)  : ItemMedia()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaMultimedia() {
    val context = LocalContext.current

    var mostrarCamara   by remember { mutableStateOf(false) }
    var mediaSeleccionado by remember { mutableStateOf<ItemMedia?>(null) }
    var galeriaItems    by remember { mutableStateOf<List<ItemMedia>>(emptyList()) }

    // Permisos necesarios
    var permisoCamara by remember {
        mutableStateOf(
            PermisosHelper.tienePermiso(context, android.Manifest.permission.CAMERA)
        )
    }
    val launcherPermisoCamara = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { permisoCamara = it }

    // PhotoPicker — imágenes
    val launcherImagen = rememberLauncherForActivityResult(
        ActivityResultContracts.PickMultipleVisualMedia(maxItems = 10)
    ) { uris ->
        galeriaItems = galeriaItems + uris.map { ItemMedia.Imagen(it) }
    }

    // PhotoPicker — videos
    val launcherVideo = rememberLauncherForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        uri?.let { galeriaItems = galeriaItems + ItemMedia.Video(it) }
    }

    // Si se está mostrando la cámara, ocupa toda la pantalla
    if (mostrarCamara) {
        PantallaCamara(
            onFotoTomada = { uri ->
                galeriaItems    = galeriaItems + ItemMedia.Imagen(uri)
                mediaSeleccionado = ItemMedia.Imagen(uri)
                mostrarCamara   = false
            },
            onCerrar = { mostrarCamara = false }
        )
        return
    }

    // Vista principal
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Multimedia", fontWeight = FontWeight.Bold) }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            // Botones de acción
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Cámara
                Button(
                    onClick = {
                        if (permisoCamara) mostrarCamara = true
                        else launcherPermisoCamara.launch(
                            android.Manifest.permission.CAMERA
                        )
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(Icons.Default.CameraAlt, null)
                    Spacer(Modifier.width(4.dp))
                    Text("Cámara")
                }

                // Galería imágenes
                OutlinedButton(
                    onClick  = {
                        launcherImagen.launch(
                            PickVisualMediaRequest(
                                ActivityResultContracts.PickVisualMedia.ImageOnly
                            )
                        )
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(Icons.Default.PhotoLibrary, null)
                    Spacer(Modifier.width(4.dp))
                    Text("Galería")
                }

                // Video
                OutlinedButton(
                    onClick  = {
                        launcherVideo.launch(
                            PickVisualMediaRequest(
                                ActivityResultContracts.PickVisualMedia.VideoOnly
                            )
                        )
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(Icons.Default.VideoLibrary, null)
                    Spacer(Modifier.width(4.dp))
                    Text("Video")
                }
            }

            // Visor del item seleccionado
            AnimatedVisibility(visible = mediaSeleccionado != null) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(260.dp)
                        .padding(horizontal = 16.dp)
                        .clip(RoundedCornerShape(16.dp))
                ) {
                    when (val item = mediaSeleccionado) {
                        is ItemMedia.Imagen -> VisorImagen(
                            uri      = item.uri,
                            modifier = Modifier.fillMaxSize()
                        )
                        is ItemMedia.Video  -> ReproductorVideo(
                            uri      = item.uri,
                            modifier = Modifier.fillMaxSize()
                        )
                        null -> {}
                    }

                    // Botón cerrar visor
                    IconButton(
                        onClick  = { mediaSeleccionado = null },
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(4.dp)
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(Color.Black.copy(alpha = 0.5f))
                    ) {
                        Icon(Icons.Default.Close, "Cerrar", tint = Color.White, modifier = Modifier.size(18.dp))
                    }
                }
            }

            Spacer(Modifier.height(8.dp))

            // Grid de miniaturas
            if (galeriaItems.isEmpty()) {
                Box(
                    modifier         = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            Icons.Default.PermMedia,
                            contentDescription = null,
                            modifier = Modifier.size(64.dp),
                            tint     = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(Modifier.height(8.dp))
                        Text(
                            "Toma una foto o selecciona de la galería",
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            } else {
                LazyVerticalGrid(
                    columns         = GridCells.Fixed(3),
                    modifier        = Modifier.fillMaxSize(),
                    contentPadding  = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalArrangement   = Arrangement.spacedBy(4.dp)
                ) {
                    items(galeriaItems) { item ->
                        Box(
                            modifier = Modifier
                                .aspectRatio(1f)
                                .clip(RoundedCornerShape(8.dp))
                                .clickable { mediaSeleccionado = item }
                        ) {
                            when (item) {
                                is ItemMedia.Imagen -> AsyncImage(
                                    model              = item.uri,
                                    contentDescription = null,
                                    contentScale       = ContentScale.Crop,
                                    modifier           = Modifier.fillMaxSize()
                                )
                                is ItemMedia.Video  -> {
                                    Box(
                                        Modifier
                                            .fillMaxSize()
                                            .background(Color.Black),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            Icons.Default.PlayCircle,
                                            contentDescription = "Video",
                                            tint     = Color.White,
                                            modifier = Modifier.size(40.dp)
                                        )
                                    }
                                }
                            }

                            // Indicador de seleccionado
                            if (mediaSeleccionado == item) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .border(
                                            3.dp,
                                            MaterialTheme.colorScheme.primary,
                                            RoundedCornerShape(8.dp)
                                        )
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}