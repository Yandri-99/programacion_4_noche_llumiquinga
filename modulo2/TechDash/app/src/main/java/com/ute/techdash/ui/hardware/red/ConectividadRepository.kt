package com.ute.techdash.ui.hardware.red

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged

enum class EstadoRed { CONECTADO_WIFI, CONECTADO_DATOS, SIN_CONEXION }

class ConectividadRepository(context: Context) {

    private val conectividadManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    // Estado actual (instantáneo)
    fun estadoActual(): EstadoRed {
        val red     = conectividadManager.activeNetwork ?: return EstadoRed.SIN_CONEXION
        val caps    = conectividadManager.getNetworkCapabilities(red) ?: return EstadoRed.SIN_CONEXION
        return when {
            caps.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)     -> EstadoRed.CONECTADO_WIFI
            caps.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> EstadoRed.CONECTADO_DATOS
            else -> EstadoRed.SIN_CONEXION
        }
    }

    // Flow reactivo — emite cuando cambia la conectividad
    fun conectividadFlow(): Flow<EstadoRed> = callbackFlow {
        // Enviar estado inicial
        trySend(estadoActual())

        val callback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                val caps = conectividadManager.getNetworkCapabilities(network)
                val estado = when {
                    caps?.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)     == true
                        -> EstadoRed.CONECTADO_WIFI
                    caps?.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) == true
                        -> EstadoRed.CONECTADO_DATOS
                    else -> EstadoRed.CONECTADO_DATOS
                }
                trySend(estado)
            }

            override fun onLost(network: Network) {
                trySend(EstadoRed.SIN_CONEXION)
            }

            override fun onCapabilitiesChanged(
                network:              Network,
                networkCapabilities:  NetworkCapabilities
            ) {
                val estado = when {
                    networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)     ->
                        EstadoRed.CONECTADO_WIFI
                    networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ->
                        EstadoRed.CONECTADO_DATOS
                    else -> EstadoRed.SIN_CONEXION
                }
                trySend(estado)
            }
        }

        val peticion = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()

        conectividadManager.registerNetworkCallback(peticion, callback)

        awaitClose {
            conectividadManager.unregisterNetworkCallback(callback)
        }
    }.distinctUntilChanged()   // solo emite cuando realmente cambia
}