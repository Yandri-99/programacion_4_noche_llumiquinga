package com.ute.techdash.ui.multimedia

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Forward10
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Replay10
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.LocalLifecycleOwner
import android.view.TextureView
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer

@Composable
fun ReproductorVideo(
    uri:      Uri,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    // Crear y recordar el reproductor
    val reproductor = remember(uri) {
        ExoPlayer.Builder(context).build().apply {
            setMediaItem(MediaItem.fromUri(uri))
            prepare()
            playWhenReady = true   // reproducir automáticamente
        }
    }

    // Limpiar el reproductor cuando el composable sale de la composición
    DisposableEffect(reproductor) {
        onDispose {
            reproductor.release()  // IMPORTANTE — liberar recursos
        }
    }

    // Pausar/reanudar según el ciclo de vida
    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer = androidx.lifecycle.LifecycleEventObserver { _, event ->
            when (event) {
                androidx.lifecycle.Lifecycle.Event.ON_PAUSE  -> reproductor.pause()
                androidx.lifecycle.Lifecycle.Event.ON_RESUME -> reproductor.play()
                else -> {}
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
    }

    Box(modifier = modifier.background(Color.Black)) {
        // Superficie de video
        // TextureView no tiene problemas de z-order en Compose (funciona en Android 9-11+)
        // SurfaceView (que usa PlayerView internamente) puede mostrar negro en versiones antiguas
        AndroidView(
            factory  = { ctx ->
                TextureView(ctx).also { tv ->
                    reproductor.setVideoTextureView(tv)
                }
            },
            modifier = Modifier.fillMaxSize()
        )

        // Controles personalizados
        ControlesVideo(reproductor = reproductor)
    }
}

@Composable
fun ControlesVideo(reproductor: Player) {
    var reproduciendo by remember { mutableStateOf(reproductor.isPlaying) }
    var progreso      by remember { mutableFloatStateOf(0f) }
    var duracion      by remember { mutableLongStateOf(0L) }

    // Actualizar progreso periódicamente
    LaunchedEffect(reproductor) {
        while (true) {
            reproduciendo = reproductor.isPlaying
            progreso      = if (reproductor.duration > 0)
                reproductor.currentPosition.toFloat() / reproductor.duration
            else 0f
            duracion = reproductor.duration
            kotlinx.coroutines.delay(500)
        }
    }

    Box(
        modifier         = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(Color.Black.copy(alpha = 0.6f))
                .padding(12.dp)
        ) {
            // Barra de progreso
            LinearProgressIndicator(
                progress = { progreso },
                modifier = Modifier.fillMaxWidth(),
                color    = Color.White
            )

            Spacer(Modifier.height(8.dp))

            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment     = Alignment.CenterVertically
            ) {
                // Tiempo actual / duración
                Text(
                    "${formatearTiempo(reproductor.currentPosition)} / ${formatearTiempo(duracion)}",
                    color = Color.White,
                    style = MaterialTheme.typography.labelMedium
                )

                // Controles
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    // Retroceder 10s
                    IconButton(onClick = {
                        reproductor.seekTo((reproductor.currentPosition - 10_000).coerceAtLeast(0))
                    }) {
                        Icon(Icons.Default.Replay10, null, tint = Color.White)
                    }

                    // Play / Pause
                    IconButton(onClick = {
                        if (reproductor.isPlaying) reproductor.pause()
                        else reproductor.play()
                    }) {
                        Icon(
                            if (reproduciendo) Icons.Default.Pause else Icons.Default.PlayArrow,
                            null,
                            tint     = Color.White,
                            modifier = Modifier.size(32.dp)
                        )
                    }

                    // Avanzar 10s
                    IconButton(onClick = {
                        reproductor.seekTo(
                            (reproductor.currentPosition + 10_000)
                                .coerceAtMost(reproductor.duration)
                        )
                    }) {
                        Icon(Icons.Default.Forward10, null, tint = Color.White)
                    }
                }
            }
        }
    }
}

fun formatearTiempo(ms: Long): String {
    if (ms <= 0) return "0:00"
    val seg  = (ms / 1000) % 60
    val min  = (ms / 1000) / 60
    val hora = min / 60
    return if (hora > 0) "%d:%02d:%02d".format(hora, min % 60, seg)
    else                 "%d:%02d".format(min, seg)
}