// viewmodel/ProductosViewModel.kt
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

class ProductosViewModel : ViewModel() {

    // Estado privado mutable — solo el ViewModel lo modifica
    private val _productos = MutableStateFlow<List<Producto>>(emptyList())
    // Estado público de solo lectura — la UI lo observa
    val productos: StateFlow<List<Producto>> = _productos.asStateFlow()

    private val _busqueda = MutableStateFlow("")
    val busqueda: StateFlow<String> = _busqueda.asStateFlow()

    private val _cargando = MutableStateFlow(false)
    val cargando: StateFlow<Boolean> = _cargando.asStateFlow()

    // Lista completa en memoria — la fuente de verdad local
    private var todosLosProductos: List<Producto> = emptyList()

    // init se ejecuta cuando el ViewModel se crea por primera vez
    init {
        cargarProductos()
    }

    private fun cargarProductos() {
        // viewModelScope.launch: coroutine segura atada al ViewModel
        viewModelScope.launch {
            _cargando.value = true
            delay(800) // simula latencia de red — en Paso 5 será una llamada real
            todosLosProductos = productosDeMuestra
            _productos.value = todosLosProductos
            _cargando.value = false
        }
    }

    fun actualizarBusqueda(query: String) {
        _busqueda.value = query
        // Filtra la lista local sin llamar a la red
        _productos.value = if (query.isBlank()) {
            todosLosProductos
        } else {
            todosLosProductos.filter {
                it.nombre.contains(query, ignoreCase = true) ||
                        it.categoria.contains(query, ignoreCase = true)
            }
        }
    }

    fun recargar() { cargarProductos() }
}