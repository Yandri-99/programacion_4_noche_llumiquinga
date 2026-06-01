package com.ute.transporte.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ute.transporte.model.Vehiculo
import com.ute.transporte.model.vehiculosDeMuestra
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class UiState<out T> {
    object Loading                        : UiState<Nothing>()
    data class Success<T>(val data: T)    : UiState<T>()
    data class Error(val message: String) : UiState<Nothing>()
}

class VehiculosUiStateViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<Vehiculo>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<Vehiculo>>> = _uiState.asStateFlow()

    private val _busqueda = MutableStateFlow("")
    val busqueda: StateFlow<String> = _busqueda.asStateFlow()

    private var todosLosVehiculos: List<Vehiculo> = emptyList()

    init { cargarVehiculos() }

    fun cargarVehiculos(simularError: Boolean = false) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            delay(800)

            if (simularError) {
                _uiState.value = UiState.Error("Error de conexión (simulado)")
                return@launch
            }

            try {
                todosLosVehiculos = vehiculosDeMuestra
                _uiState.value = UiState.Success(todosLosVehiculos)
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message ?: "Error desconocido")
            }
        }
    }

    fun actualizarBusqueda(query: String) {
        _busqueda.value = query
        val actual = _uiState.value
        if (actual is UiState.Success) {
            _uiState.value = UiState.Success(
                if (query.isBlank()) todosLosVehiculos
                else todosLosVehiculos.filter {
                    it.nombre.contains(query, ignoreCase = true) ||
                            it.tipo.contains(query, ignoreCase = true)
                }
            )
        }
    }

    fun recargar() { cargarVehiculos() }
}
