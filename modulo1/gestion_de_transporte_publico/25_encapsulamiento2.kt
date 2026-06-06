
class Vehiculo(conductor: String, capacidadInicial: Int) {

    val conductor: String = conductor

    private var pasajeros: Int = capacidadInicial

    internal val codigoVehiculo: String =
        "VH${(1000..9999).random()}"

    private fun calcularDesgaste(): Double = pasajeros.toDouble() * 0.05

    fun abordarPasajeros(cantidad: Int) {
        require(cantidad > 0) { "La cantidad debe ser positiva" }
        pasajeros += cantidad
        println("Abordaron: $cantidad | Total: ${consultarOcupacion()}")
    }

    fun bajarPasajeros(cantidad: Int): Boolean {
        require(cantidad > 0) { "La cantidad debe ser positiva" }
        if (cantidad > pasajeros) {
            println("No hay suficientes pasajeros")
            return false
        }
        pasajeros -= cantidad
        println("Bajaron: $cantidad | Total: ${consultarOcupacion()}")
        return true
    }

    fun consultarOcupacion(): String = "$pasajeros pasajeros"
}

fun main() {
    val bus = Vehiculo("Carlos Perez", 0)

    bus.abordarPasajeros(30)
    bus.bajarPasajeros(10)
    bus.bajarPasajeros(50)

    println(bus.conductor)
    println(bus.consultarOcupacion())
}

class VelocidadBus(kmh: Double) {

    var kmh: Double = kmh
        set(value) {
            require(value >= 0) { "La velocidad no puede ser negativa" }
            field = value
        }

    val mph: Double
        get() = kmh * 0.621371

    val ms: Double
        get() = kmh / 3.6

    val descripcion: String
        get() = when {
            kmh < 10 -> "Muy lento"
            kmh < 30 -> "Lento"
            kmh < 50 -> "Moderado"
            kmh < 70 -> "Rapido"
            else -> "Muy rapido"
        }
}

fun main() {
    val vel = VelocidadBus(40.0)
    println("${vel.kmh} km/h = ${vel.mph} mph = ${vel.ms} m/s")
    println(vel.descripcion)

    vel.kmh = 5.0
    println("${vel.kmh} km/h -> ${vel.descripcion}")
}
