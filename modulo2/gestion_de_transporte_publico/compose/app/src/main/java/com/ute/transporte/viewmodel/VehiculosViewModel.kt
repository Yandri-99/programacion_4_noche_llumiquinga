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

class VehiculosViewModel : ViewModel() {

    private val _vehiculos = MutableStateFlow<List<Vehiculo>>(emptyList())
    val vehiculos: StateFlow<List<Vehiculo>> = _vehiculos.asStateFlow()

    private val _busqueda = MutableStateFlow("")
    val busqueda: StateFlow<String> = _busqueda.asStateFlow()

    private val _cargando = MutableStateFlow(false)
    val cargando: StateFlow<Boolean> = _cargando.asStateFlow()

    private var todosLosVehiculos: List<Vehiculo> = emptyList()

    init {
        cargarVehiculos()
    }

    private fun cargarVehiculos() {
        viewModelScope.launch {
            _cargando.value = true
            delay(800)
            todosLosVehiculos = vehiculosDeMuestra
            _vehiculos.value = todosLosVehiculos
            _cargando.value = false
        }
    }

    fun actualizarBusqueda(query: String) {
        _busqueda.value = query
        _vehiculos.value = if (query.isBlank()) {
            todosLosVehiculos
        } else {
            todosLosVehiculos.filter {
                it.nombre.contains(query, ignoreCase = true) ||
                        it.tipo.contains(query, ignoreCase = true)
            }
        }
    }

    fun recargar() { cargarVehiculos() }
}
