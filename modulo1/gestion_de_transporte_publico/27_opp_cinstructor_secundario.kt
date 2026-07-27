
class RutaTransporte(val distanciaKm: Double, val tiempoMin: Double) {
    val velocidadProm: Double get() = (distanciaKm / (tiempoMin / 60))
    val costoOperativo: Double get() = distanciaKm * 2.5

    constructor(distanciaKm: Double) : this(distanciaKm, distanciaKm * 2)
    constructor(distanciaKm: Int, tiempoMin: Int) : this(distanciaKm.toDouble(), tiempoMin.toDouble())

    override fun toString() = "Ruta(${distanciaKm}km x ${tiempoMin}min) | vel=${"%.1f".format(velocidadProm)} km/h"
}

fun main() {
    val r1 = RutaTransporte(25.0, 45.0)
    val r2 = RutaTransporte(15.0)
    val r3 = RutaTransporte(30, 60)

    println(r1)
    println(r2)
}
