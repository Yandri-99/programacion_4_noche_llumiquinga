package com.transportapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.transportapp.domain.model.Trip
import com.transportapp.domain.repository.TripRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface TripDetailUiState {
    data object Loading                    : TripDetailUiState
    data class  Success(val trip: Trip)    : TripDetailUiState
    data class  Error(val message: String) : TripDetailUiState
}

@HiltViewModel
class TripDetailViewModel @Inject constructor(
    private val repository: TripRepository,
) : ViewModel() {

    private val _state = MutableStateFlow<TripDetailUiState>(TripDetailUiState.Loading)
    val state: StateFlow<TripDetailUiState> = _state.asStateFlow()

    fun load(id: Int) {
        viewModelScope.launch {
            _state.value = TripDetailUiState.Loading
            repository.getTrip(id)
                .onSuccess { _state.value = TripDetailUiState.Success(it) }
                .onFailure { _state.value = TripDetailUiState.Error(it.message ?: "Error") }
        }
    }
}
