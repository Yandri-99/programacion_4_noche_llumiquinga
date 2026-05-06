package com.ute.compose.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ute.compose.model.Producto
import com.ute.compose.model.productosDeMuestra
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// sealed class en el mismo archivo del ViewModel — alternativa: archivo propio
sealed class UiState<out T> {
    object Loading                        : UiState<Nothing>()
    data class Success<T>(val data: T)    : UiState<T>()
    data class Error(val message: String) : UiState<Nothing>()
}

class ProductosUiStateViewModel : ViewModel() {

    // Un solo StateFlow con todos los estados posibles
    private val _uiState = MutableStateFlow<UiState<List<Producto>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<Producto>>> = _uiState.asStateFlow()

    private val _busqueda = MutableStateFlow("")
    val busqueda: StateFlow<String> = _busqueda.asStateFlow()

    private var todosLosProductos: List<Producto> = emptyList()

    init { cargarProductos() }

    fun cargarProductos(simularError: Boolean = false) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            delay(800)

            if (simularError) {
                // En el Paso 5 esto vendrá de una excepción real de Retrofit
                _uiState.value = UiState.Error("Error de conexión (simulado)")
                return@launch
            }

            try {
                todosLosProductos = productosDeMuestra
                _uiState.value = UiState.Success(todosLosProductos)
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
                if (query.isBlank()) todosLosProductos
                else todosLosProductos.filter {
                    it.nombre.contains(query, ignoreCase = true) ||
                            it.categoria.contains(query, ignoreCase = true)
                }
            )
        }
    }

    fun recargar() { cargarProductos() }
}