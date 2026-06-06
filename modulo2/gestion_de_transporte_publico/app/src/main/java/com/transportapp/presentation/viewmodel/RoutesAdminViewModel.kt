package com.transportapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.transportapp.domain.model.Route
import com.transportapp.domain.model.RoutePayload
import com.transportapp.domain.repository.RouteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class RoutesAdminUiState(
    val routes:     List<Route> = emptyList(),
    val isLoading:  Boolean     = false,
    val error:      String?     = null,
    val search:     String      = "",
)

sealed interface RouteFormState {
    data object Idle                          : RouteFormState
    data object Saving                        : RouteFormState
    data class  Success(val msg: String)      : RouteFormState
    data class  Error(val message: String)    : RouteFormState
}

@HiltViewModel
class RoutesAdminViewModel @Inject constructor(
    private val repository: RouteRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(RoutesAdminUiState())
    val state: StateFlow<RoutesAdminUiState> = _state.asStateFlow()

    private val _formState = MutableStateFlow<RouteFormState>(RouteFormState.Idle)
    val formState: StateFlow<RouteFormState> = _formState.asStateFlow()

    val filtered: StateFlow<List<Route>> = _state
        .map { s ->
            if (s.search.isBlank()) s.routes
            else s.routes.filter {
                it.name.contains(s.search, ignoreCase = true) ||
                it.origin.contains(s.search, ignoreCase = true) ||
                it.destination.contains(s.search, ignoreCase = true)
            }
        }
        .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    init { load() }

    fun load() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            repository.getRoutes()
                .onSuccess { routes ->
                    _state.update { it.copy(routes = routes, isLoading = false) }
                }
                .onFailure { e ->
                    _state.update { it.copy(isLoading = false, error = e.message) }
                }
        }
    }

    fun setSearch(query: String) {
        _state.update { it.copy(search = query) }
    }

    fun createRoute(payload: RoutePayload) {
        _formState.value = RouteFormState.Saving
        viewModelScope.launch {
            repository.createRoute(payload)
                .onSuccess { created ->
                    _state.update { s ->
                        s.copy(routes = listOf(created) + s.routes)
                    }
                    _formState.value = RouteFormState.Success("Ruta creada")
                }
                .onFailure { e ->
                    _formState.value = RouteFormState.Error(e.message ?: "Error al crear")
                }
        }
    }

    fun updateRoute(id: Int, payload: RoutePayload) {
        _formState.value = RouteFormState.Saving
        viewModelScope.launch {
            repository.updateRoute(id, payload)
                .onSuccess { updated ->
                    _state.update { s ->
                        s.copy(routes = s.routes.map {
                            if (it.id == id) updated else it
                        })
                    }
                    _formState.value = RouteFormState.Success("Ruta actualizada")
                }
                .onFailure { e ->
                    _formState.value = RouteFormState.Error(e.message ?: "Error al actualizar")
                }
        }
    }

    fun deleteRoute(id: Int) {
        viewModelScope.launch {
            repository.deleteRoute(id)
                .onSuccess {
                    _state.update { s ->
                        s.copy(routes = s.routes.filter { it.id != id })
                    }
                }
                .onFailure { e ->
                    _state.update { it.copy(error = e.message) }
                }
        }
    }

    fun resetFormState() { _formState.value = RouteFormState.Idle }
}
