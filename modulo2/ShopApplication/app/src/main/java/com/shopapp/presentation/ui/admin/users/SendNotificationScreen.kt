package com.shopapp.presentation.ui.admin.users

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.shopapp.presentation.viewmodel.SendNotificationViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SendNotificationScreen(
    onBack:    () -> Unit,
    viewModel: SendNotificationViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()
    var subject   by remember { mutableStateOf("") }
    var message   by remember { mutableStateOf("") }
    var userIdStr by remember { mutableStateOf("") }

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(state.result) {
        state.result?.let { result ->
            val msg = buildString {
                append("✅ Enviado a ${result.sent} usuario(s)")
                if (result.failed > 0) append(" — ${result.failed} fallido(s)")
            }
            snackbarHostState.showSnackbar(msg)
            viewModel.clearResult()
        }
    }

    LaunchedEffect(state.error) {
        state.error?.let {
            snackbarHostState.showSnackbar("Error: $it")
            viewModel.clearError()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Enviar notificación") },
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
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState()),
        ) {
            Spacer(Modifier.height(16.dp))

            OutlinedTextField(
                value           = subject,
                onValueChange   = { subject = it },
                label           = { Text("Asunto") },
                singleLine      = true,
                enabled         = !state.isLoading,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                modifier        = Modifier.fillMaxWidth(),
            )

            Spacer(Modifier.height(12.dp))

            OutlinedTextField(
                value         = message,
                onValueChange = { message = it },
                label         = { Text("Mensaje") },
                minLines      = 5,
                maxLines      = 10,
                enabled       = !state.isLoading,
                modifier      = Modifier.fillMaxWidth(),
            )

            Spacer(Modifier.height(12.dp))

            OutlinedTextField(
                value           = userIdStr,
                onValueChange   = { userIdStr = it.filter { c -> c.isDigit() } },
                label           = { Text("ID de usuario (opcional)") },
                placeholder     = { Text("Dejar vacío para envío masivo") },
                singleLine      = true,
                enabled         = !state.isLoading,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction    = ImeAction.Done,
                ),
                supportingText  = {
                    Text(
                        if (userIdStr.isBlank())
                            "Sin ID → se envía a todos los usuarios activos no-staff"
                        else
                            "Con ID → se envía solo al usuario #$userIdStr"
                    )
                },
                modifier = Modifier.fillMaxWidth(),
            )

            Spacer(Modifier.height(24.dp))

            val userId      = userIdStr.toIntOrNull()
            val isFormValid = subject.isNotBlank() && message.isNotBlank()

            Button(
                onClick  = { viewModel.send(subject.trim(), message.trim(), userId) },
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
                    Text("Enviando...")
                } else {
                    Icon(
                        imageVector        = Icons.Default.Send,
                        contentDescription = null,
                        modifier           = Modifier.size(18.dp),
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(
                        text = if (userId != null) "Enviar al usuario #$userId"
                               else "Enviar a todos"
                    )
                }
            }

            Spacer(Modifier.height(16.dp))
        }
    }
}
