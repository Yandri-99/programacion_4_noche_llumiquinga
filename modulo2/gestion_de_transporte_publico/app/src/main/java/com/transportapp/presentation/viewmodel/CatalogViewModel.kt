package com.transportapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.transportapp.domain.model.Route
import com.transportapp.domain.model.Vehicle
import com.transportapp.domain.model.VehicleFilters
import com.transportapp.domain.repository.RouteRepository
import com.transportapp.domain.repository.VehicleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject

data class CatalogUiState(
    val vehicles:         List<Vehicle> = emptyList(),
    val routes:           List<Route> = emptyList(),
    val isLoading:        Boolean = false,
    val isLoadingMore:    Boolean = false,
    val error:            String? = null,
    val total:            Int     = 0,
    val hasMore:          Boolean = false,
    val search:           String  = "",
    val selectedRoute:    Int?    = null,
    val selectedTipo:     String? = null,
    val ordering:         String  = "",
    val page:             Int     = 1,
)

@HiltViewModel
class CatalogViewModel @Inject constructor(
    private val vehicleRepository: VehicleRepository,
    private val routeRepository: RouteRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(CatalogUiState())
    val state: StateFlow<CatalogUiState> = _state.asStateFlow()

    private var searchJob: Job? = null

    init { loadRoutes(); load() }

    private fun loadRoutes() {
        viewModelScope.launch {
            routeRepository.getRoutes().onSuccess { routes ->
                _state.update { it.copy(routes = routes) }
            }
        }
    }

    fun load(reset: Boolean = true) {
        val current = _state.value
        val page    = if (reset) 1 else current.page

        if (reset) {
            _state.update { it.copy(isLoading = true, error = null, page = 1) }
        } else {
            if (current.isLoadingMore || !current.hasMore) return
            _state.update { it.copy(isLoadingMore = true) }
        }

        viewModelScope.launch {
            val filters = VehicleFilters(
                search   = current.search.ifBlank { null },
                tipo     = current.selectedTipo,
                estado   = null,
                ordering = current.ordering.ifBlank { null },
                page     = page,
                pageSize = 12,
            )
            vehicleRepository.getVehicles(filters)
                .onSuccess { (vehicles, total) ->
                    _state.update { s ->
                        s.copy(
                            vehicles      = if (reset) vehicles else s.vehicles + vehicles,
                            total         = total,
                            hasMore       = (if (reset) vehicles else s.vehicles + vehicles).size < total,
                            isLoading     = false,
                            isLoadingMore = false,
                            page          = page + 1,
                            error         = null,
                        )
                    }
                }
                .onFailure { e ->
                    _state.update { it.copy(isLoading = false, isLoadingMore = false, error = e.message) }
                }
        }
    }

    fun setSearch(query: String) {
        _state.update { it.copy(search = query) }
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(400)
            load(reset = true)
        }
    }

    fun setRoute(id: Int?) {
        _state.update { it.copy(selectedRoute = id) }
        load(reset = true)
    }

    fun setTipo(tipo: String?) {
        _state.update { it.copy(selectedTipo = tipo) }
        load(reset = true)
    }

    fun setOrdering(ordering: String) {
        _state.update { it.copy(ordering = ordering) }
        load(reset = true)
    }

    fun loadMore() = load(reset = false)
    fun refresh()  = load(reset = true)
}
