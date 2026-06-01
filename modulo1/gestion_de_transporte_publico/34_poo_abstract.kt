
abstract class VehiculoMotorizado(val nombre: String) {
    abstract val capacidad: Int
    abstract val costoOperativoKm: Double
    abstract fun descripcion(): String

    fun comparar(otro: VehiculoMotorizado): String = when {
        capacidad > otro.capacidad -> "$nombre tiene mas capacidad que ${otro.nombre}"
        capacidad < otro.capacidad -> "$nombre tiene menos capacidad que ${otro.nombre}"
        else -> "$nombre y ${otro.nombre} tienen la misma capacidad"
    }

    override fun toString() = "${descripcion()} | Capacidad: $capacidad"
}

class BusUrbano(val placa: String, val pasajerosMax: Int) : VehiculoMotorizado("Bus Urbano") {
    override val capacidad: Int get() = pasajerosMax
    override val costoOperativoKm: Double get() = 2.5
    override fun descripcion() = "Bus Urbano placa $placa"
}

class Microbus(val placa: String, val asientos: Int) : VehiculoMotorizado("Microbus") {
    override val capacidad: Int get() = asientos
    override val costoOperativoKm: Double get() = 1.8
    override fun descripcion() = "Microbus placa $placa"
}

class BusArticuladoTransporte(val placa: String, val pasajerosMax: Int) : VehiculoMotorizado("Bus Articulado") {
    override val capacidad: Int get() = pasajerosMax
    override val costoOperativoKm: Double get() = 3.5
    override fun descripcion() = "Bus Articulado placa $placa"
}

fun main() {
    val vehiculos: List<VehiculoMotorizado> = listOf(
        BusUrbano("PAB-1234", 45),
        Microbus("XYZ-5678", 25),
        BusArticuladoTransporte("ABC-9012", 60)
    )

    vehiculos.forEach { println(it) }

    val mayor = vehiculos.maxByOrNull { it.capacidad }
    println("\nVehiculo con mayor capacidad: ${mayor?.nombre}")

    println(vehiculos[0].comparar(vehiculos[1]))
}
