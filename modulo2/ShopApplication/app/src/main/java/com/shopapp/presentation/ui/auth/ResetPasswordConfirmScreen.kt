package com.shopapp.presentation.ui.auth

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.shopapp.presentation.viewmodel.ResetPasswordConfirmViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResetPasswordConfirmScreen(
    onBack:         () -> Unit,
    onResetSuccess: () -> Unit,
    viewModel:      ResetPasswordConfirmViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()
    var uid          by remember { mutableStateOf("") }
    var token        by remember { mutableStateOf("") }
    var newPassword  by remember { mutableStateOf("") }
    var newPassword2 by remember { mutableStateOf("") }
    var showPass     by remember { mutableStateOf(false) }

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(state.resetSuccess) {
        if (state.resetSuccess) {
            snackbarHostState.showSnackbar("Contraseña actualizada. Inicia sesión.")
            onResetSuccess()
        }
    }

    LaunchedEffect(state.error) {
        state.error?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.clearError()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Nueva contraseña") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector        = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver",
                        )
                    }
                },
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) { padding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState()),
        ) {
            Spacer(Modifier.height(24.dp))

            Icon(
                imageVector        = Icons.Default.Lock,
                contentDescription = null,
                tint               = MaterialTheme.colorScheme.primary,
                modifier           = Modifier.size(48.dp),
            )

            Spacer(Modifier.height(12.dp))

            Text(
                text  = "Pega el uid y el token del enlace que recibiste por correo y elige una nueva contraseña.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )

            Spacer(Modifier.height(24.dp))

            OutlinedTextField(
                value           = uid,
                onValueChange   = { uid = it },
                label           = { Text("UID") },
                placeholder     = { Text("ej. MQ") },
                singleLine      = true,
                enabled         = !state.isLoading,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                modifier        = Modifier.fillMaxWidth(),
            )

            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                value           = token,
                onValueChange   = { token = it },
                label           = { Text("Token") },
                placeholder     = { Text("ej. abc-defg-hij") },
                singleLine      = true,
                enabled         = !state.isLoading,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                modifier        = Modifier.fillMaxWidth(),
            )

            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                value                = newPassword,
                onValueChange        = { newPassword = it },
                label                = { Text("Nueva contraseña") },
                singleLine           = true,
                enabled              = !state.isLoading,
                visualTransformation = if (showPass) VisualTransformation.None
                                       else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = { showPass = !showPass }) {
                        Icon(
                            imageVector        = if (showPass) Icons.Default.VisibilityOff
                                                else Icons.Default.Visibility,
                            contentDescription = if (showPass) "Ocultar contraseña"
                                                else "Mostrar contraseña",
                        )
                    }
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction    = ImeAction.Next,
                ),
                modifier = Modifier.fillMaxWidth(),
            )

            Spacer(Modifier.height(8.dp))

            val passwordMismatch = newPassword2.isNotEmpty() && newPassword != newPassword2

            OutlinedTextField(
                value                = newPassword2,
                onValueChange        = { newPassword2 = it },
                label                = { Text("Confirmar contraseña") },
                singleLine           = true,
                enabled              = !state.isLoading,
                visualTransformation = if (showPass) VisualTransformation.None
                                       else PasswordVisualTransformation(),
                isError              = passwordMismatch,
                supportingText       = {
                    if (passwordMismatch) Text("Las contraseñas no coinciden")
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction    = ImeAction.Done,
                ),
                modifier = Modifier.fillMaxWidth(),
            )

            Spacer(Modifier.height(24.dp))

            val isFormValid = uid.isNotBlank() && token.isNotBlank()
                && newPassword.isNotBlank() && newPassword == newPassword2

            Button(
                onClick  = {
                    viewModel.confirmReset(uid, token, newPassword, newPassword2)
                },
                enabled  = isFormValid && !state.isLoading,
                modifier = Modifier.fillMaxWidth(),
            ) {
                if (state.isLoading) {
                    CircularProgressIndicator(
                        modifier    = Modifier.size(18.dp),
                        color       = MaterialTheme.colorScheme.onPrimary,
                        strokeWidth = 2.dp,
                    )
                    Spacer(Modifier.width(8.dp))
                }
                Text(if (state.isLoading) "Guardando..." else "Restablecer contraseña")
            }

            Spacer(Modifier.height(16.dp))
        }
    }
}
