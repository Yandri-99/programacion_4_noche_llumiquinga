package com.transportapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.transportapp.domain.model.Driver
import com.transportapp.domain.model.DriverPayload
import com.transportapp.domain.repository.DriverRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject

enum class DriverStatusFilter(val label: String) {
    ALL("Todos"),
    DISPONIBLE("Disponibles"),
    NO_DISPONIBLE("No disponibles"),
    ACTIVE("Activos"),
    INACTIVE("Inactivos"),
}

data class DriversAdminUiState(
    val drivers:     List<Driver>        = emptyList(),
    val isLoading:   Boolean             = false,
    val error:       String?             = null,
    val total:       Int                 = 0,
    val search:      String              = "",
    val statusFilter: DriverStatusFilter = DriverStatusFilter.ALL,
)

sealed interface DriverFormState {
    data object Idle                          : DriverFormState
    data object Saving                        : DriverFormState
    data class  Success(val msg: String)      : DriverFormState
    data class  Error(val message: String)    : DriverFormState
}

@HiltViewModel
class DriversAdminViewModel @Inject constructor(
    private val repository: DriverRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(DriversAdminUiState())
    val state: StateFlow<DriversAdminUiState> = _state.asStateFlow()

    private val _formState = MutableStateFlow<DriverFormState>(DriverFormState.Idle)
    val formState: StateFlow<DriverFormState> = _formState.asStateFlow()

    val filtered: StateFlow<List<Driver>> = _state
        .map { s ->
            s.drivers
                .filter { d ->
                    s.search.isBlank() ||
                            d.nombre.contains(s.search, ignoreCase = true) ||
                            d.email.contains(s.search, ignoreCase = true)
                }
                .filter { d ->
                    when (s.statusFilter) {
                        DriverStatusFilter.ALL            -> true
                        DriverStatusFilter.DISPONIBLE     -> d.disponible
                        DriverStatusFilter.NO_DISPONIBLE  -> !d.disponible
                        DriverStatusFilter.ACTIVE         -> d.isActive
                        DriverStatusFilter.INACTIVE       -> !d.isActive
                    }
                }
        }
        .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    private var searchJob: Job? = null

    init { load() }

    fun load() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            repository.getDrivers()
                .onSuccess { (drivers, total) ->
                    _state.update { it.copy(drivers = drivers, total = total, isLoading = false) }
                }
                .onFailure { e ->
                    _state.update { it.copy(isLoading = false, error = e.message) }
                }
        }
    }

    fun setSearch(query: String) {
        _state.update { it.copy(search = query) }
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(300)
        }
    }

    fun setStatusFilter(filter: DriverStatusFilter) {
        _state.update { it.copy(statusFilter = filter) }
    }

    fun toggleDisponible(id: Int, disponible: Boolean) {
        _state.update { s ->
            s.copy(drivers = s.drivers.map { d ->
                if (d.id == id) d.copy(disponible = disponible) else d
            })
        }
        viewModelScope.launch {
            val driver = _state.value.drivers.first { it.id == id }
            repository.updateDriver(id, DriverPayload(
                email       = driver.email,
                nombre      = driver.nombre,
                telefono    = driver.telefono,
                licencia    = driver.licencia,
                disponible  = disponible,
                isActive    = driver.isActive,
            )).onFailure {
                _state.update { s ->
                    s.copy(drivers = s.drivers.map { d ->
                        if (d.id == id) d.copy(disponible = !disponible) else d
                    })
                }
            }
        }
    }

    fun toggleActive(id: Int) {
        val driver = _state.value.drivers.find { it.id == id } ?: return
        val next = !driver.isActive
        _state.update { s ->
            s.copy(drivers = s.drivers.map { d ->
                if (d.id == id) d.copy(isActive = next) else d
            })
        }
        viewModelScope.launch {
            repository.toggleActive(id)
                .onSuccess { serverActive ->
                    _state.update { s ->
                        s.copy(drivers = s.drivers.map { d ->
                            if (d.id == id) d.copy(isActive = serverActive) else d
                        })
                    }
                }
                .onFailure {
                    _state.update { s ->
                        s.copy(drivers = s.drivers.map { d ->
                            if (d.id == id) d.copy(isActive = !next) else d
                        })
                    }
                }
        }
    }

    fun createDriver(payload: DriverPayload) {
        _formState.value = DriverFormState.Saving
        viewModelScope.launch {
            repository.createDriver(payload)
                .onSuccess { created ->
                    _state.update { s ->
                        s.copy(drivers = listOf(created) + s.drivers, total = s.total + 1)
                    }
                    _formState.value = DriverFormState.Success("Conductor creado")
                }
                .onFailure { e ->
                    _formState.value = DriverFormState.Error(e.message ?: "Error al crear")
                }
        }
    }

    fun updateDriver(id: Int, payload: DriverPayload) {
        _formState.value = DriverFormState.Saving
        viewModelScope.launch {
            repository.updateDriver(id, payload)
                .onSuccess { updated ->
                    _state.update { s ->
                        s.copy(drivers = s.drivers.map { if (it.id == id) updated else it })
                    }
                    _formState.value = DriverFormState.Success("Conductor actualizado")
                }
                .onFailure { e ->
                    _formState.value = DriverFormState.Error(e.message ?: "Error al actualizar")
                }
        }
    }

    fun deleteDriver(id: Int) {
        viewModelScope.launch {
            repository.deleteDriver(id)
                .onSuccess {
                    _state.update { s ->
                        s.copy(drivers = s.drivers.filter { it.id != id }, total = s.total - 1)
                    }
                }
                .onFailure { e ->
                    _state.update { it.copy(error = e.message) }
                }
        }
    }

    fun resetFormState() { _formState.value = DriverFormState.Idle }
}
