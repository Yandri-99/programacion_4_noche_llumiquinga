package com.transportapp.presentation.ui.vehicles

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.transportapp.domain.model.Vehicle
import com.transportapp.domain.repository.VehicleRepository
import com.transportapp.presentation.components.*
import com.transportapp.presentation.viewmodel.CartViewModel
import com.transportapp.theme.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface VehicleDetailUiState {
    data object Loading                        : VehicleDetailUiState
    data class  Success(val vehicle: Vehicle)  : VehicleDetailUiState
    data class  Error(val message: String)     : VehicleDetailUiState
}

@HiltViewModel
class VehicleDetailViewModel @Inject constructor(
    private val repository: VehicleRepository,
) : ViewModel() {

    private val _state = MutableStateFlow<VehicleDetailUiState>(VehicleDetailUiState.Loading)
    val state: StateFlow<VehicleDetailUiState> = _state.asStateFlow()

    fun load(id: Int) {
        viewModelScope.launch {
            _state.value = VehicleDetailUiState.Loading
            repository.getVehicle(id)
                .onSuccess { _state.value = VehicleDetailUiState.Success(it) }
                .onFailure { _state.value = VehicleDetailUiState.Error(it.message ?: "Error") }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VehicleDetailScreen(
    vehicleId:   Int,
    onBack:      () -> Unit,
    cartViewModel: CartViewModel,
    viewModel:   VehicleDetailViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(vehicleId) { viewModel.load(vehicleId) }

    when (val s = state) {
        is VehicleDetailUiState.Loading -> LoadingScreen("Cargando vehículo...")
        is VehicleDetailUiState.Error   -> ErrorScreen(s.message, onRetry = { viewModel.load(vehicleId) })
        is VehicleDetailUiState.Success -> VehicleDetailContent(
            vehicle              = s.vehicle,
            onBack               = onBack,
            cartViewModel = cartViewModel,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun VehicleDetailContent(
    vehicle:              Vehicle,
    onBack:               () -> Unit,
    cartViewModel: CartViewModel,
) {
    var pasajeros by remember { mutableIntStateOf(1) }
    var reserved  by remember { mutableStateOf(false) }

    val total = vehicle.precioPasaje * pasajeros

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .verticalScroll(rememberScrollState()),
    ) {
        Box(modifier = Modifier.fillMaxWidth().height(260.dp)) {
            Box(
                modifier         = Modifier.fillMaxSize().background(Surface2),
                contentAlignment = Alignment.Center,
            ) {
                Text("🚌", fontSize = 72.sp)
            }

            IconButton(
                onClick  = onBack,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(8.dp)
                    .background(Background.copy(alpha = 0.7f), RoundedCornerShape(50)),
            ) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Volver", tint = TextPrimary)
            }
        }

        Column(modifier = Modifier.padding(24.dp)) {

            Text(
                text       = vehicle.tipo.uppercase(),
                style      = MaterialTheme.typography.labelSmall,
                color      = Accent,
                letterSpacing = 1.sp,
            )
            Spacer(Modifier.height(6.dp))

            Text(
                text       = vehicle.name,
                style      = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color      = TextPrimary,
            )
            Spacer(Modifier.height(12.dp))

            Text(
                text       = "$${"%.2f".format(vehicle.precioPasaje)}",
                fontSize   = 32.sp,
                fontWeight = FontWeight.Bold,
                color      = Accent,
            )
            Text(
                text  = "Precio por pasajero",
                style = MaterialTheme.typography.bodySmall,
                color = TextSecondary,
            )
            Spacer(Modifier.height(16.dp))

            HorizontalDivider(color = Border, thickness = 0.5.dp)
            Spacer(Modifier.height(16.dp))

            InfoRow(label = "Placa", value = vehicle.placa)
            Spacer(Modifier.height(8.dp))
            InfoRow(label = "Capacidad", value = "${vehicle.capacidad} pasajeros")
            Spacer(Modifier.height(8.dp))
            InfoRow(label = "Estado", value = vehicle.estado.replaceFirstChar { it.uppercase() })
            Spacer(Modifier.height(8.dp))
            vehicle.routeName?.let {
                InfoRow(label = "Ruta", value = it)
            }

            if (vehicle.description.isNotBlank()) {
                Spacer(Modifier.height(16.dp))
                HorizontalDivider(color = Border, thickness = 0.5.dp)
                Spacer(Modifier.height(16.dp))
                Text(
                    text  = vehicle.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextSecondary,
                )
                Spacer(Modifier.height(16.dp))
            }

            HorizontalDivider(color = Border, thickness = 0.5.dp)
            Spacer(Modifier.height(16.dp))

            Row(
                modifier          = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text       = "Pasajeros",
                    style      = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color      = TextPrimary,
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(
                        onClick  = { if (pasajeros > 1) pasajeros-- },
                        enabled  = pasajeros > 1,
                    ) {
                        Icon(
                            Icons.Default.Remove,
                            contentDescription = "Menos",
                            tint = if (pasajeros > 1) TextPrimary else TextFaint,
                        )
                    }
                    Text(
                        text       = pasajeros.toString(),
                        style      = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color      = TextPrimary,
                        modifier   = Modifier.padding(horizontal = 16.dp),
                    )
                    IconButton(
                        onClick = { if (pasajeros < vehicle.capacidad) pasajeros++ },
                        enabled = pasajeros < vehicle.capacidad,
                    ) {
                        Icon(
                            Icons.Default.Add,
                            contentDescription = "Más",
                            tint = if (pasajeros < vehicle.capacidad) TextPrimary else TextFaint,
                        )
                    }
                }
            }

            Spacer(Modifier.height(12.dp))
            Surface(
                color  = Surface2,
                shape  = MaterialTheme.shapes.medium,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Row(
                    modifier              = Modifier.padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment     = Alignment.CenterVertically,
                ) {
                    Text("Total", color = TextSecondary, style = MaterialTheme.typography.bodyMedium)
                    Text(
                        text       = "$${"%.2f".format(total)}",
                        style      = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color      = Accent,
                    )
                }
            }
            Spacer(Modifier.height(20.dp))

            Button(
                onClick = {
                    cartViewModel.addItem(vehicle, pasajeros)
                    reserved = true
                },
                enabled  = !reserved,
                modifier = Modifier.fillMaxWidth().height(52.dp),
                colors   = ButtonDefaults.buttonColors(
                    containerColor         = if (reserved) Success else Accent,
                    contentColor           = AccentOnDark,
                    disabledContainerColor = Success,
                    disabledContentColor   = AccentOnDark,
                ),
                shape    = MaterialTheme.shapes.medium,
            ) {
                Icon(
                    imageVector        = if (reserved) Icons.Default.Check else Icons.Default.DateRange,
                    contentDescription = null,
                    modifier           = Modifier.size(18.dp),
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    text       = if (reserved) "¡Reservado!" else
                        "Reservar${if (pasajeros > 1) " $pasajeros×" else ""}",
                    fontWeight = FontWeight.Bold,
                    style      = MaterialTheme.typography.labelLarge,
                )
            }

            LaunchedEffect(reserved) {
                if (reserved) {
                    kotlinx.coroutines.delay(2_000)
                    reserved = false
                }
            }
        }
    }
}

@Composable
private fun InfoRow(label: String, value: String) {
    Row(
        modifier              = Modifier.fillMaxWidth(),
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
}
