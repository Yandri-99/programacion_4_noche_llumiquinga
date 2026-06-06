package com.transportapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.transportapp.data.local.TokenDataStore
import com.transportapp.domain.model.LoggedDriver
import com.transportapp.domain.repository.AuthRepository
import com.transportapp.presentation.ui.auth.AuthUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val tokenDataStore: TokenDataStore,
) : ViewModel() {

    private val _uiState = MutableStateFlow<AuthUiState>(AuthUiState.Idle)
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    private val _currentDriver = MutableStateFlow<LoggedDriver?>(null)
    val currentDriver: StateFlow<LoggedDriver?> = _currentDriver.asStateFlow()

    val isAuthenticated: StateFlow<Boolean> = _currentDriver
        .map { it != null }
        .stateIn(viewModelScope, SharingStarted.Eagerly, false)

    val isStaff: StateFlow<Boolean> = _currentDriver
        .map { it?.isStaff == true }
        .stateIn(viewModelScope, SharingStarted.Eagerly, false)

    private val _isCheckingSession = MutableStateFlow(true)
    val isCheckingSession: StateFlow<Boolean> = _isCheckingSession.asStateFlow()

    init {
        restoreSession()
    }

    private fun restoreSession() {
        viewModelScope.launch {
            try {
                val snapshot = authRepository.getStoredDriver()
                if (snapshot != null && authRepository.isLoggedIn()) {
                    _currentDriver.value = LoggedDriver(
                        id      = snapshot.id,
                        email   = snapshot.email,
                        nombre  = snapshot.username,
                        isStaff = snapshot.isStaff,
                    )
                }
            } finally {
                _isCheckingSession.value = false
            }
        }
    }

    fun login(email: String, password: String) {
        if (_uiState.value is AuthUiState.Loading) return
        viewModelScope.launch {
            _uiState.value = AuthUiState.Loading
            authRepository.login(email.trim(), password)
                .onSuccess { driver ->
                    _currentDriver.value = driver
                    _uiState.value = AuthUiState.Success(driver)
                }
                .onFailure { e ->
                    _uiState.value = AuthUiState.Error(e.message ?: "Error al iniciar sesión")
                }
        }
    }

    fun register(nombre: String, email: String, password: String, password2: String) {
        if (_uiState.value is AuthUiState.Loading) return
        viewModelScope.launch {
            _uiState.value = AuthUiState.Loading
            authRepository.register(nombre.trim(), email.trim(), password, password2)
                .onSuccess { driver ->
                    _currentDriver.value = driver
                    _uiState.value = AuthUiState.Success(driver)
                }
                .onFailure { e ->
                    _uiState.value = AuthUiState.Error(e.message ?: "Error al registrarse")
                }
        }
    }

    fun logout() {
        viewModelScope.launch {
            authRepository.logout()
            _currentDriver.value = null
            _uiState.value = AuthUiState.Idle
        }
    }

    fun clearError() {
        if (_uiState.value is AuthUiState.Error) {
            _uiState.value = AuthUiState.Idle
        }
    }
}
