package com.ute.techdash.ui.permisos

import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.NoPhotography
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat

@Composable
fun PantallaConPermiso() {
    val context = LocalContext.current

    // Estado del permiso
    var permisoOtorgado by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                android.Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        )
    }
    var mostrarRacional by remember { mutableStateOf(false) }

    // Launcher para solicitar UN permiso
    val lanzadorPermiso = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { concedido ->
        permisoOtorgado = concedido
        if (!concedido) mostrarRacional = true
    }

    Column(
        Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement   = Arrangement.Center,
        horizontalAlignment   = Alignment.CenterHorizontally
    ) {
        if (permisoOtorgado) {
            Icon(
                Icons.Default.CameraAlt,
                contentDescription = null,
                modifier = Modifier.size(64.dp),
                tint     = MaterialTheme.colorScheme.primary
            )
            Spacer(Modifier.height(16.dp))
            Text("Permiso de cámara concedido ✅")
            Spacer(Modifier.height(8.dp))
            Text(
                "Aquí iría el componente de cámara",
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        } else {
            Icon(
                Icons.Default.NoPhotography,
                contentDescription = null,
                modifier = Modifier.size(64.dp),
                tint     = MaterialTheme.colorScheme.error
            )
            Spacer(Modifier.height(16.dp))
            Text("Se necesita acceso a la cámara")
            Spacer(Modifier.height(8.dp))
            Button(onClick = {
                lanzadorPermiso.launch(android.Manifest.permission.CAMERA)
            }) {
                Text("Solicitar permiso")
            }
        }

        // Diálogo explicativo cuando el usuario deniega
        if (mostrarRacional) {
            AlertDialog(
                onDismissRequest = { mostrarRacional = false },
                title = { Text("Permiso necesario") },
                text  = {
                    Text(
                        "La cámara es necesaria para tomar fotos. " +
                                "Por favor, concede el permiso en los ajustes de la app."
                    )
                },
                confirmButton = {
                    Button(onClick = {
                        mostrarRacional = false
                        // Abrir ajustes de la app
                        val intent = android.content.Intent(
                            android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                            android.net.Uri.fromParts("package", context.packageName, null)
                        )
                        context.startActivity(intent)
                    }) { Text("Ir a ajustes") }
                },
                dismissButton = {
                    OutlinedButton(onClick = { mostrarRacional = false }) {
                        Text("Cancelar")
                    }
                }
            )
        }
    }
}