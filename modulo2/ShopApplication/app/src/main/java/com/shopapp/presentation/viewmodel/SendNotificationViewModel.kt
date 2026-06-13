package com.shopapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shopapp.domain.model.NotificationResult
import com.shopapp.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SendNotificationUiState(
    val isLoading: Boolean             = false,
    val result:    NotificationResult? = null,
    val error:     String?             = null,
)

@HiltViewModel
class SendNotificationViewModel @Inject constructor(
    private val repository: UserRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(SendNotificationUiState())
    val state: StateFlow<SendNotificationUiState> = _state.asStateFlow()

    fun send(subject: String, message: String, userId: Int?) {
        if (_state.value.isLoading) return
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null, result = null) }
            repository.sendNotification(subject, message, userId)
                .onSuccess { result ->
                    _state.update { it.copy(isLoading = false, result = result) }
                }
                .onFailure { e ->
                    _state.update { it.copy(isLoading = false, error = e.message) }
                }
        }
    }

    fun clearResult() { _state.update { it.copy(result = null) } }
    fun clearError()  { _state.update { it.copy(error = null) } }
}
