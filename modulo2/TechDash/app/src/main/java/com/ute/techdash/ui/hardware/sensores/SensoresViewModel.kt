package com.ute.techdash.ui.hardware.sensores

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class SensoresState(
    val acelerometro: DatoAcelerometro? = null,
    val giroscopio:   DatoGiroscopio?   = null,
    val luz:          DatoLuz?           = null,
    val activo:       Boolean            = false
)

class SensoresViewModel(
    private val repositorio: SensoresRepository
) : ViewModel() {

    private val _state = MutableStateFlow(SensoresState())
    val state: StateFlow<SensoresState> = _state.asStateFlow()

    private var jobs = listOf<Job>()

    fun iniciar() {
        if (_state.value.activo) return
        _state.update { it.copy(activo = true) }

        jobs = listOf(
            viewModelScope.launch {
                repositorio.acelerometroFlow()
                    .catch { }
                    .collect { _state.update { s -> s.copy(acelerometro = it) } }
            },
            viewModelScope.launch {
                repositorio.giroscopioFlow()
                    .catch { }
                    .collect { _state.update { s -> s.copy(giroscopio = it) } }
            },
            viewModelScope.launch {
                repositorio.luzAmbienteFlow()
                    .catch { }
                    .collect { _state.update { s -> s.copy(luz = it) } }
            }
        )
    }

    fun detener() {
        jobs.forEach { it.cancel() }
        jobs = emptyList()
        _state.update { it.copy(activo = false) }
    }

    override fun onCleared() { super.onCleared(); detener() }

    companion object {
        fun factory(context: Context) = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T =
                SensoresViewModel(SensoresRepository(context)) as T
        }
    }
}