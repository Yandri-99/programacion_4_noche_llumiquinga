package com.shopapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shopapp.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ResetPasswordConfirmUiState(
    val isLoading:    Boolean = false,
    val resetSuccess: Boolean = false,
    val error:        String? = null,
)

@HiltViewModel
class ResetPasswordConfirmViewModel @Inject constructor(
    private val repository: AuthRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(ResetPasswordConfirmUiState())
    val state: StateFlow<ResetPasswordConfirmUiState> = _state.asStateFlow()

    fun confirmReset(
        uid:          String,
        token:        String,
        newPassword:  String,
        newPassword2: String,
    ) {
        if (_state.value.isLoading) return
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            repository.confirmReset(uid.trim(), token.trim(), newPassword, newPassword2)
                .onSuccess {
                    _state.update { it.copy(isLoading = false, resetSuccess = true) }
                }
                .onFailure { e ->
                    _state.update { it.copy(isLoading = false, error = e.message) }
                }
        }
    }

    fun clearError() {
        _state.update { it.copy(error = null) }
    }
}
