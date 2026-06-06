package com.transportapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.transportapp.domain.model.Vehicle
import com.transportapp.domain.model.VehicleFilters
import com.transportapp.domain.repository.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class DashboardStats(
    val totalVehiculos:          Int    = 0,
    val vehiculosActivos:        Int    = 0,
    val vehiculosInactivos:      Int    = 0,
    val capacidadPromedio:       Double = 0.0,
    val totalRutas:              Int    = 0,
    val totalViajes:             Int    = 0,
    val totalIngresos:           Double = 0.0,
    val viajesPendientes:        Int    = 0,
    val viajesPorEstado:         Map<String, Int> = emptyMap(),
    val conductoresActivos:      Int    = 0,
    val totalConductores:        Int    = 0,
    val conductoresDisponibles:  Int    = 0,
    val vehiculosBajaCapacidad:  List<Vehicle> = emptyList(),
)

sealed interface DashboardUiState {
    data object Loading                            : DashboardUiState
    data class  Success(val stats: DashboardStats) : DashboardUiState
    data class  Error(val message: String)         : DashboardUiState
}

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val vehicleRepository: VehicleRepository,
    private val routeRepository: RouteRepository,
    private val tripRepository: TripRepository,
    private val driverRepository: DriverRepository,
) : ViewModel() {

    private val _state = MutableStateFlow<DashboardUiState>(DashboardUiState.Loading)
    val state: StateFlow<DashboardUiState> = _state.asStateFlow()

    private val _lastUpdated = MutableStateFlow<Long>(0L)
    val lastUpdated: StateFlow<Long> = _lastUpdated.asStateFlow()

    init { load() }

    fun load() {
        viewModelScope.launch {
            _state.value = DashboardUiState.Loading

            try {
                val vehicleStatsDeferred = async { vehicleRepository.getStats() }
                val routeStatsDeferred   = async { routeRepository.getStats() }
                val tripStatsDeferred    = async { tripRepository.getStats() }
                val driverStatsDeferred  = async { driverRepository.getStats() }
                val lowCapDeferred       = async {
                    vehicleRepository.getVehicles(
                        VehicleFilters(
                            ordering = "capacidad",
                            pageSize = 5,
                        )
                    )
                }

                val vehicleStats = vehicleStatsDeferred.await().getOrThrow()
                val routeStats   = routeStatsDeferred.await().getOrThrow()
                val tripStats    = tripStatsDeferred.await().getOrThrow()
                val driverStats  = driverStatsDeferred.await().getOrThrow()
                val lowCap       = lowCapDeferred.await().getOrNull()

                @Suppress("UNCHECKED_CAST")
                val viajesPorEstado = (tripStats["by_status"] as? Map<String, Int>) ?: emptyMap()

                val stats = DashboardStats(
                    totalVehiculos         = (vehicleStats["total_active"]   as? Int)    ?: 0,
                    vehiculosActivos       = (vehicleStats["total_active"]   as? Int)    ?: 0,
                    vehiculosInactivos     = (vehicleStats["total_inactive"] as? Int)    ?: 0,
                    capacidadPromedio      = (vehicleStats["avg_capacidad"]  as? Double) ?: 0.0,
                    totalRutas             = (routeStats["total"]            as? Int)    ?: 0,
                    totalViajes            = (tripStats["total_trips"]       as? Int)    ?: 0,
                    totalIngresos          = (tripStats["total_revenue"]     as? Double) ?: 0.0,
                    viajesPendientes       = viajesPorEstado["pendiente"]                ?: 0,
                    viajesPorEstado        = viajesPorEstado,
                    conductoresActivos     = (driverStats["active"]          as? Int)    ?: 0,
                    totalConductores       = (driverStats["total"]           as? Int)    ?: 0,
                    conductoresDisponibles = (driverStats["inactive"]        as? Int)    ?: 0,
                    vehiculosBajaCapacidad = lowCap?.first
                        ?.filter { it.capacidad < 10 }
                        ?.take(5)
                        ?: emptyList(),
                )

                _state.value       = DashboardUiState.Success(stats)
                _lastUpdated.value = System.currentTimeMillis()

            } catch (e: Exception) {
                _state.value = DashboardUiState.Error(e.message ?: "Error al cargar el dashboard")
            }
        }
    }
}
