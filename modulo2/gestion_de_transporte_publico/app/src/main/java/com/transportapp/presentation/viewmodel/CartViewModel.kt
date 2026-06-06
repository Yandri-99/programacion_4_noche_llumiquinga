package com.transportapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.transportapp.domain.model.Vehicle
import com.transportapp.domain.repository.TripRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ReservationItem(
    val vehicle: Vehicle,
    val cantidadPasajeros: Int,
)

sealed interface ReservationState {
    data object Idle                          : ReservationState
    data object Loading                       : ReservationState
    data class  Success(val tripId: Int)      : ReservationState
    data class  Error(val message: String)    : ReservationState
}

@HiltViewModel
class CartViewModel @Inject constructor(
    private val tripRepository: TripRepository,
) : ViewModel() {

    private val _items = MutableStateFlow<List<ReservationItem>>(emptyList())
    val items: StateFlow<List<ReservationItem>> = _items.asStateFlow()

    val totalItems: StateFlow<Int> = _items
        .map { it.size }
        .stateIn(viewModelScope, SharingStarted.Eagerly, 0)

    val totalPasajeros: StateFlow<Int> = _items
        .map { it.sumOf { i -> i.cantidadPasajeros } }
        .stateIn(viewModelScope, SharingStarted.Eagerly, 0)

    val totalPrecio: StateFlow<Double> = _items
        .map { it.sumOf { i -> i.vehicle.precioPasaje * i.cantidadPasajeros } }
        .stateIn(viewModelScope, SharingStarted.Eagerly, 0.0)

    private val _reservationState = MutableStateFlow<ReservationState>(ReservationState.Idle)
    val reservationState: StateFlow<ReservationState> = _reservationState.asStateFlow()

    fun addItem(vehicle: Vehicle, cantidadPasajeros: Int = 1) {
        _items.update { list ->
            val existing = list.find { it.vehicle.id == vehicle.id }
            if (existing != null) {
                list.map {
                    if (it.vehicle.id == vehicle.id)
                        it.copy(cantidadPasajeros = minOf(it.cantidadPasajeros + cantidadPasajeros, vehicle.capacidad))
                    else it
                }
            } else {
                list + ReservationItem(vehicle, cantidadPasajeros)
            }
        }
    }

    fun updatePasajeros(vehicleId: Int, cantidad: Int) {
        if (cantidad <= 0) removeItem(vehicleId)
        else _items.update { list ->
            list.map { if (it.vehicle.id == vehicleId) it.copy(cantidadPasajeros = cantidad) else it }
        }
    }

    fun removeItem(vehicleId: Int) {
        _items.update { it.filter { i -> i.vehicle.id != vehicleId } }
    }

    fun clearCart() { _items.value = emptyList() }

    fun resetReservation() { _reservationState.value = ReservationState.Idle }

    fun reservar() {
        val currentItems = _items.value
        if (currentItems.isEmpty()) {
            _reservationState.value = ReservationState.Error("No hay vehículos seleccionados")
            return
        }
        viewModelScope.launch {
            _reservationState.value = ReservationState.Loading

            val item = currentItems.first()
            val trip = tripRepository.createTrip(
                origin    = item.vehicle.routeName ?: "Desconocido",
                destination = item.vehicle.routeName ?: "Desconocido",
                pasajeros = item.cantidadPasajeros,
                total     = item.vehicle.precioPasaje * item.cantidadPasajeros,
                routeId   = item.vehicle.routeId,
                vehicleId = item.vehicle.id,
            ).getOrElse {
                _reservationState.value = ReservationState.Error(it.message ?: "Error al crear viaje")
                return@launch
            }

            clearCart()
            _reservationState.value = ReservationState.Success(trip.id)
        }
    }
}
