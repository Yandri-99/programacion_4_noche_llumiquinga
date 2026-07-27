// 📁 Nuevos archivos → ui/hardware/gps/LocationRepository.kt  (incluye UbicacionDato y LocationRepository)
package com.ute.techdash.ui.hardware.gps

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.os.Looper
import com.google.android.gms.location.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

data class UbicacionDato(
    val latitud:   Double,
    val longitud:  Double,
    val precision: Float,    // metros de precisión
    val altitud:   Double,
    val velocidad: Float,    // m/s
    val timestamp: Long
) {
    val coordenadas get() = "${"%.6f".format(latitud)}, ${"%.6f".format(longitud)}"
    val precisionTexto get() = "±${"%.0f".format(precision)}m"
}

fun Location.toUbicacionDato() = UbicacionDato(
    latitud   = latitude,
    longitud  = longitude,
    precision = accuracy,
    altitud   = altitude,
    velocidad = speed,
    timestamp = time
)

class LocationRepository(private val context: Context) {

    private val clienteUbicacion: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    // Configuración de las actualizaciones
    private val peticionUbicacion = LocationRequest.Builder(
        Priority.PRIORITY_HIGH_ACCURACY,
        5_000L    // intervalo deseado: 5 segundos
    )
        .setMinUpdateIntervalMillis(2_000L)     // mínimo: 2 segundos
        .setMaxUpdateDelayMillis(10_000L)       // máximo delay: 10 segundos
        .setMinUpdateDistanceMeters(5f)         // solo si se movió 5 metros
        .build()

    // Flow de ubicaciones continuas
    @SuppressLint("MissingPermission")
    fun ubicacionFlow(): Flow<UbicacionDato> = callbackFlow {
        val callback = object : LocationCallback() {
            override fun onLocationResult(resultado: LocationResult) {
                resultado.lastLocation?.let { location ->
                    trySend(location.toUbicacionDato())
                }
            }
        }

        clienteUbicacion.requestLocationUpdates(
            peticionUbicacion,
            callback,
            Looper.getMainLooper()
        )

        // Cuando el Flow se cancela, eliminar el callback
        awaitClose {
            clienteUbicacion.removeLocationUpdates(callback)
        }
    }

    // Obtener la última ubicación conocida (instantánea)
    @SuppressLint("MissingPermission")
    suspend fun ultimaUbicacionConocida(): UbicacionDato? {
        return clienteUbicacion.lastLocation.await()?.toUbicacionDato()
    }
}