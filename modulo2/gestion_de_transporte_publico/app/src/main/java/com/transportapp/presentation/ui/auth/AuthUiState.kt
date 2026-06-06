package com.transportapp.presentation.ui.auth

import com.transportapp.domain.model.LoggedDriver

sealed interface AuthUiState {
    data object Idle        : AuthUiState
    data object Loading     : AuthUiState
    data class  Success(val driver: LoggedDriver) : AuthUiState
    data class  Error(val message: String)        : AuthUiState
}
