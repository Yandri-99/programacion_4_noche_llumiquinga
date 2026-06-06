package com.transportapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.transportapp.domain.model.Route
import com.transportapp.domain.model.Vehicle
import com.transportapp.domain.model.VehicleFilters
import com.transportapp.domain.model.VehiclePayload
import com.transportapp.domain.repository.RouteRepository
import com.transportapp.domain.repository.VehicleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class VehicleStatusFilter(val label: String) {
    ALL("Todos"),
    ACTIVO("Activo"),
    MANTENIMIENTO("Mantenimiento"),
    FUERA_DE_SERVICIO("Fuera de servicio"),
}

data class VehiclesAdminUiState(
    val vehicles:  List<Vehicle>       = emptyList(),
    val isLoading: Boolean             = false,
    val error:     String?             = null,
    val total:     Int                 = 0,
    val search:    String              = "",
    val statusFilter: VehicleStatusFilter = VehicleStatusFilter.ALL,
)

sealed interface VehicleFormState {
    data object Idle                          : VehicleFormState
    data object Saving                        : VehicleFormState
    data class  Success(val msg: String)      : VehicleFormState
    data class  Error(val message: String)    : VehicleFormState
}

@HiltViewModel
class VehiclesAdminViewModel @Inject constructor(
    private val repository: VehicleRepository,
    private val routeRepository: RouteRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(VehiclesAdminUiState())
    val state: StateFlow<VehiclesAdminUiState> = _state.asStateFlow()

    private val _formState = MutableStateFlow<VehicleFormState>(VehicleFormState.Idle)
    val formState: StateFlow<VehicleFormState> = _formState.asStateFlow()

    private val _routes = MutableStateFlow<List<Route>>(emptyList())
    val routes: StateFlow<List<Route>> = _routes.asStateFlow()

    val filtered: StateFlow<List<Vehicle>> = _state
        .map { s ->
            s.vehicles
                .filter { v ->
                    s.search.isBlank() || v.name.contains(s.search, ignoreCase = true)
                }
                .filter { v ->
                    when (s.statusFilter) {
                        VehicleStatusFilter.ALL              -> true
                        VehicleStatusFilter.ACTIVO           -> v.estado == "activo"
                        VehicleStatusFilter.MANTENIMIENTO     -> v.estado == "mantenimiento"
                        VehicleStatusFilter.FUERA_DE_SERVICIO -> v.estado == "fuera_de_servicio"
                    }
                }
        }
        .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    init {
        load()
        loadRoutes()
    }

    fun load() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            repository.getVehicles(VehicleFilters(pageSize = 50))
                .onSuccess { (vehicles, total) ->
                    _state.update { it.copy(vehicles = vehicles, total = total, isLoading = false) }
                }
                .onFailure { e ->
                    _state.update { it.copy(isLoading = false, error = e.message) }
                }
        }
    }

    private fun loadRoutes() {
        viewModelScope.launch {
            routeRepository.getRoutes()
                .onSuccess { _routes.value = it }
        }
    }

    fun setSearch(query: String)                       = _state.update { it.copy(search = query) }
    fun setStatusFilter(filter: VehicleStatusFilter) = _state.update { it.copy(statusFilter = filter) }

    fun toggleActivo(id: Int, isActive: Boolean) {
        val estado = if (isActive) "activo" else "inactivo"
        _state.update { s ->
            s.copy(vehicles = s.vehicles.map {
                if (it.id == id) it.copy(estado = estado) else it
            })
        }
        viewModelScope.launch {
            val vehicle = _state.value.vehicles.firstOrNull { it.id == id } ?: return@launch
            repository.updateVehicle(
                id, VehiclePayload(
                    name        = vehicle.name,
                    description = vehicle.description,
                    placa       = vehicle.placa,
                    tipo        = vehicle.tipo,
                    capacidad   = vehicle.capacidad,
                    precioPasaje = vehicle.precioPasaje,
                    estado      = estado,
                    routeId     = vehicle.routeId ?: 0,
                )
            ).onFailure {
                _state.update { s ->
                    s.copy(vehicles = s.vehicles.map { v ->
                        if (v.id == id) v.copy(estado = vehicle.estado) else v
                    })
                }
            }
        }
    }

    fun createVehicle(payload: VehiclePayload) {
        _formState.value = VehicleFormState.Saving
        viewModelScope.launch {
            repository.createVehicle(payload)
                .onSuccess { created ->
                    _state.update { s ->
                        s.copy(vehicles = listOf(created) + s.vehicles, total = s.total + 1)
                    }
                    _formState.value = VehicleFormState.Success("Vehículo creado")
                }
                .onFailure { e ->
                    _formState.value = VehicleFormState.Error(e.message ?: "Error al crear")
                }
        }
    }

    fun updateVehicle(id: Int, payload: VehiclePayload) {
        _formState.value = VehicleFormState.Saving
        viewModelScope.launch {
            repository.updateVehicle(id, payload)
                .onSuccess { updated ->
                    _state.update { s ->
                        s.copy(vehicles = s.vehicles.map { if (it.id == id) updated else it })
                    }
                    _formState.value = VehicleFormState.Success("Vehículo actualizado")
                }
                .onFailure { e ->
                    _formState.value = VehicleFormState.Error(e.message ?: "Error al actualizar")
                }
        }
    }

    fun updateVehicleEstado(id: Int, estado: String) {
        val vehicle = _state.value.vehicles.firstOrNull { it.id == id } ?: return
        _state.update { s ->
            s.copy(vehicles = s.vehicles.map { if (it.id == id) it.copy(estado = estado) else it })
        }
        viewModelScope.launch {
            repository.updateVehicle(
                id, VehiclePayload(
                    name         = vehicle.name,
                    description  = vehicle.description,
                    placa        = vehicle.placa,
                    tipo         = vehicle.tipo,
                    capacidad    = vehicle.capacidad,
                    precioPasaje = vehicle.precioPasaje,
                    estado       = estado,
                    routeId      = vehicle.routeId ?: 0,
                )
            ).onFailure {
                _state.update { s ->
                    s.copy(vehicles = s.vehicles.map { v ->
                        if (v.id == id) v.copy(estado = vehicle.estado) else v
                    })
                }
            }
        }
    }

    fun deleteVehicle(id: Int) {
        viewModelScope.launch {
            repository.deleteVehicle(id)
                .onSuccess {
                    _state.update { s ->
                        s.copy(
                            vehicles = s.vehicles.filter { it.id != id },
                            total    = s.total - 1,
                        )
                    }
                }
                .onFailure { e ->
                    _state.update { it.copy(error = e.message) }
                }
        }
    }

    fun resetFormState() {
        _formState.value = VehicleFormState.Idle
    }
}
