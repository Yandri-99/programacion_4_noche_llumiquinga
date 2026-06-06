package com.transportapp.presentation.ui.trips

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.transportapp.domain.model.Trip
import com.transportapp.domain.model.TripStatus
import com.transportapp.presentation.components.ErrorScreen
import com.transportapp.presentation.components.LoadingScreen
import com.transportapp.presentation.components.StatusBadge
import com.transportapp.presentation.viewmodel.TripDetailUiState
import com.transportapp.presentation.viewmodel.TripDetailViewModel
import com.transportapp.theme.Accent
import com.transportapp.theme.AccentOnDark
import com.transportapp.theme.Background
import com.transportapp.theme.Border
import com.transportapp.theme.Error
import com.transportapp.theme.Surface
import com.transportapp.theme.Surface2
import com.transportapp.theme.TextFaint
import com.transportapp.theme.TextPrimary
import com.transportapp.theme.TextSecondary
import java.text.SimpleDateFormat
import java.util.Locale

private val PROGRESS_STEPS = listOf(
    TripStatus.PENDIENTE,
    TripStatus.EN_CURSO,
    TripStatus.COMPLETADO,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TripDetailScreen(
    tripId:    Int,
    onBack:    () -> Unit,
    viewModel: TripDetailViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()
    LaunchedEffect(tripId) { viewModel.load(tripId) }

    when (val s = state) {
        is TripDetailUiState.Loading ->
            LoadingScreen("Cargando viaje...")
        is TripDetailUiState.Error   ->
            ErrorScreen(s.message, onRetry = { viewModel.load(tripId) })
        is TripDetailUiState.Success ->
            TripDetailContent(trip = s.trip, onBack = onBack)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TripDetailContent(trip: Trip, onBack: () -> Unit) {
    val inputFmt  = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'", Locale.getDefault())
    val outputFmt = SimpleDateFormat("dd MMM yyyy · HH:mm", Locale("es"))
    val dateStr   = runCatching { outputFmt.format(inputFmt.parse(trip.createdAt)!!) }
        .getOrDefault(trip.createdAt.take(16))

    val isCancelled = trip.estado == TripStatus.CANCELADO
    val currentStep = PROGRESS_STEPS.indexOf(trip.estado).coerceAtLeast(0)

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            "Viaje #${trip.id}",
                            style      = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color      = TextPrimary,
                        )
                        Text(dateStr, style = MaterialTheme.typography.bodySmall, color = TextSecondary)
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver", tint = TextPrimary)
                    }
                },
                actions = { StatusBadge(trip.estado, modifier = Modifier.padding(end = 16.dp)) },
                colors  = TopAppBarDefaults.topAppBarColors(containerColor = Surface),
            )
        },
        containerColor = Background,
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {

            if (!isCancelled) {
                TripProgressBar(
                    steps       = PROGRESS_STEPS,
                    currentStep = currentStep,
                )
            } else {
                Surface(
                    color    = Error.copy(alpha = 0.08f),
                    shape    = MaterialTheme.shapes.medium,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(
                        text     = "⚠️ Este viaje fue cancelado",
                        color    = Error,
                        fontWeight = FontWeight.SemiBold,
                        style    = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(16.dp),
                    )
                }
            }

            SectionCard(title = "Ruta") {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    DetailRow("Origen", trip.origin)
                    DetailRow("Destino", trip.destination)
                    trip.routeName?.let {
                        DetailRow("Ruta", it)
                    }
                }
            }

            SectionCard(title = "Vehículo y conductor") {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    trip.driverName?.let { DetailRow("Conductor", it) }
                    trip.vehiclePlaca?.let { DetailRow("Vehículo", it) }
                    DetailRow("Pasajeros", "${trip.pasajeros}")
                }
            }

            SectionCard(title = "Resumen") {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    DetailRow("Total", "$${"%.2f".format(trip.total)}")
                }
            }

            Text(
                text  = "Actualizado: $dateStr",
                style = MaterialTheme.typography.bodySmall,
                color = TextFaint,
                modifier = Modifier.align(Alignment.CenterHorizontally),
            )
        }
    }
}

@Composable
private fun TripProgressBar(steps: List<TripStatus>, currentStep: Int) {
    Surface(
        color    = Surface,
        shape    = MaterialTheme.shapes.large,
        modifier = Modifier.fillMaxWidth(),
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(
                text       = "Estado del viaje",
                style      = MaterialTheme.typography.labelSmall,
                color      = TextSecondary,
                letterSpacing = 0.8.sp,
                modifier   = Modifier.padding(bottom = 20.dp),
            )
            Row(
                modifier          = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                steps.forEachIndexed { index, step ->
                    val isDone    = index <= currentStep
                    val isCurrent = index == currentStep
                    val color     = if (isDone) Accent else Border

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Box(
                            modifier         = Modifier
                                .size(if (isCurrent) 36.dp else 30.dp)
                                .background(
                                    if (isDone) Accent else Surface2,
                                    CircleShape,
                                ),
                            contentAlignment = Alignment.Center,
                        ) {
                            Text(
                                text       = if (isDone) "✓" else "${index + 1}",
                                color      = if (isDone) AccentOnDark else TextFaint,
                                fontSize   = if (isCurrent) 14.sp else 12.sp,
                                fontWeight = FontWeight.Bold,
                            )
                        }
                        Spacer(Modifier.height(6.dp))
                        Text(
                            text       = step.label,
                            style      = MaterialTheme.typography.bodySmall.copy(fontSize = 10.sp),
                            color      = if (isDone) Accent else TextFaint,
                            fontWeight = if (isCurrent) FontWeight.Bold else FontWeight.Normal,
                        )
                    }

                    if (index < steps.lastIndex) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(2.dp)
                                .padding(bottom = 20.dp)
                                .background(if (index < currentStep) Accent else Border),
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun SectionCard(title: String, content: @Composable ColumnScope.() -> Unit) {
    Surface(color = Surface, shape = MaterialTheme.shapes.large, modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text       = title,
                style      = MaterialTheme.typography.labelSmall,
                color      = TextSecondary,
                letterSpacing = 0.8.sp,
                modifier   = Modifier.padding(bottom = 14.dp),
            )
            content()
        }
    }
}

@Composable
private fun DetailRow(label: String, value: String) {
    Row(
        modifier              = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment     = Alignment.CenterVertically,
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
