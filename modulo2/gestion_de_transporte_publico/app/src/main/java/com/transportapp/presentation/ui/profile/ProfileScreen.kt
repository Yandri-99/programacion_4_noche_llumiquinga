package com.transportapp.presentation.ui.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.transportapp.presentation.viewmodel.AuthViewModel
import com.transportapp.theme.*

@Composable
fun ProfileScreen(
    authViewModel: AuthViewModel,
    onLogout:      () -> Unit,
) {
    val driver by authViewModel.currentDriver.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
    ) {
        Column(
            modifier            = Modifier
                .fillMaxWidth()
                .padding(vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(
                modifier         = Modifier
                    .size(80.dp)
                    .background(
                        brush = androidx.compose.ui.graphics.Brush.linearGradient(
                            listOf(Accent, AccentLight),
                        ),
                        shape = CircleShape,
                    ),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text       = driver?.nombre?.firstOrNull()?.uppercaseChar()?.toString() ?: "?",
                    fontSize   = 36.sp,
                    fontWeight = FontWeight.Bold,
                    color      = AccentOnDark,
                )
            }
            Spacer(Modifier.height(16.dp))
            Text(
                text       = driver?.nombre ?: "—",
                style      = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color      = TextPrimary,
            )
            Text(
                text  = driver?.email ?: "—",
                style = MaterialTheme.typography.bodyMedium,
                color = TextSecondary,
            )
            Spacer(Modifier.height(8.dp))
            if (driver?.isStaff == true) {
                Surface(
                    color  = Accent.copy(alpha = 0.15f),
                    shape  = MaterialTheme.shapes.extraSmall,
                ) {
                    Text(
                        text       = "Administrador",
                        color      = Accent,
                        fontSize   = 11.sp,
                        fontWeight = FontWeight.Bold,
                        modifier   = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                        letterSpacing = 0.8.sp,
                    )
                }
            }
        }

        Surface(
            color    = Surface,
            shape    = MaterialTheme.shapes.large,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text      = "Información del conductor",
                    style     = MaterialTheme.typography.labelSmall,
                    color     = TextSecondary,
                    letterSpacing = 0.8.sp,
                    modifier  = Modifier.padding(bottom = 12.dp),
                )

                val infoRows = mutableListOf(
                    "ID" to (driver?.id?.toString() ?: "—"),
                    "Nombre" to (driver?.nombre ?: "—"),
                    "Email" to (driver?.email ?: "—"),
                    "Rol" to (if (driver?.isStaff == true) "Administrador" else "Conductor"),
                )

                infoRows.forEachIndexed { i, (label, value) ->
                    Row(
                        modifier              = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Text(
                            text  = label,
                            style = MaterialTheme.typography.bodyMedium,
                            color = TextSecondary,
                        )
                        Text(
                            text       = value,
                            style      = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.SemiBold,
                            color      = TextPrimary,
                        )
                    }
                    if (i < infoRows.lastIndex) HorizontalDivider(color = BorderLight, thickness = 0.5.dp)
                }
            }
        }

        Spacer(Modifier.height(24.dp))

        var showConfirm by remember { mutableStateOf(false) }

        OutlinedButton(
            onClick  = { showConfirm = true },
            modifier = Modifier.fillMaxWidth().height(52.dp),
            colors   = ButtonDefaults.outlinedButtonColors(contentColor = Error),
            border   = ButtonDefaults.outlinedButtonBorder.copy(
                brush = androidx.compose.ui.graphics.SolidColor(Error.copy(alpha = 0.5f)),
            ),
            shape    = MaterialTheme.shapes.medium,
        ) {
            Icon(Icons.Default.Logout, contentDescription = null, modifier = Modifier.size(18.dp))
            Spacer(Modifier.width(8.dp))
            Text("Cerrar sesión", fontWeight = FontWeight.SemiBold)
        }

        if (showConfirm) {
            AlertDialog(
                onDismissRequest = { showConfirm = false },
                title            = { Text("¿Cerrar sesión?", color = TextPrimary) },
                text             = { Text("Tu sesión se cerrará en este dispositivo.", color = TextSecondary) },
                confirmButton    = {
                    TextButton(onClick = {
                        showConfirm = false
                        authViewModel.logout()
                        onLogout()
                    }) {
                        Text("Cerrar sesión", color = Error, fontWeight = FontWeight.Bold)
                    }
                },
                dismissButton    = {
                    TextButton(onClick = { showConfirm = false }) {
                        Text("Cancelar", color = TextSecondary)
                    }
                },
                containerColor   = Surface,
                shape            = MaterialTheme.shapes.large,
            )
        }
    }
}
