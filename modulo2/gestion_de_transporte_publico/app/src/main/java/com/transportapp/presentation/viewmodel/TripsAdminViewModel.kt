package com.transportapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.transportapp.domain.model.Trip
import com.transportapp.domain.model.TripStatus
import com.transportapp.domain.repository.TripRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class TripsAdminUiState(
    val trips:        List<Trip> = emptyList(),
    val isLoading:    Boolean    = false,
    val isLoadingMore:Boolean    = false,
    val error:        String?    = null,
    val total:        Int        = 0,
    val hasMore:      Boolean    = false,
    val statusFilter: String     = "",
    val page:         Int        = 1,
)

@HiltViewModel
class TripsAdminViewModel @Inject constructor(
    private val repository: TripRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(TripsAdminUiState())
    val state: StateFlow<TripsAdminUiState> = _state.asStateFlow()

    init { load() }

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
            repository.getTrips(
                page   = page,
                status = current.statusFilter.ifBlank { null },
            ).onSuccess { (trips, total) ->
                _state.update { s ->
                    s.copy(
                        trips         = if (reset) trips else s.trips + trips,
                        total         = total,
                        hasMore       = (if (reset) trips else s.trips + trips).size < total,
                        isLoading     = false,
                        isLoadingMore = false,
                        page          = page + 1,
                        error         = null,
                    )
                }
            }.onFailure { e ->
                _state.update { it.copy(isLoading = false, isLoadingMore = false, error = e.message) }
            }
        }
    }

    fun setStatusFilter(status: String) {
        _state.update { it.copy(statusFilter = status) }
        load(reset = true)
    }

    fun loadMore() = load(reset = false)
    fun refresh()  = load(reset = true)

    fun changeStatus(tripId: Int, newStatus: TripStatus) {
        val prevStatus = _state.value.trips.find { it.id == tripId }?.estado ?: return

        _state.update { s ->
            s.copy(trips = s.trips.map { t ->
                if (t.id == tripId) t.copy(estado = newStatus) else t
            })
        }

        viewModelScope.launch {
            repository.updateStatus(tripId, newStatus)
                .onFailure {
                    _state.update { s ->
                        s.copy(trips = s.trips.map { t ->
                            if (t.id == tripId) t.copy(estado = prevStatus) else t
                        })
                    }
                }
        }
    }
}
