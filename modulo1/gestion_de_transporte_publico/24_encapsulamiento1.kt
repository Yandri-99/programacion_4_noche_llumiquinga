
open class VehiculoTransporte(conductor: String, capacidadInicial: Int) {

    val conductor: String = conductor

    private var pasajeros: Int = capacidadInicial

    internal val codigoBus: String =
        "BUS${(1000..9999).random()}"

    protected open fun calcularDesgaste(): Double = pasajeros.toDouble() * 0.05

    fun abordarPasajeros(cantidad: Int) {
        require(cantidad > 0) { "La cantidad debe ser positiva" }
        pasajeros += cantidad
        println("Abordaron: $cantidad | Total a bordo: ${consultarOcupacion()}")
    }

    fun bajarPasajeros(cantidad: Int): Boolean {
        require(cantidad > 0) { "La cantidad debe ser positiva" }
        if (cantidad > pasajeros) {
            println("No hay suficientes pasajeros")
            return false
        }
        pasajeros -= cantidad
        println("Bajaron: $cantidad | Total a bordo: ${consultarOcupacion()}")
        return true
    }

    fun consultarOcupacion(): String = "$pasajeros pasajeros"
}

class BusArticulado(conductor: String, capacidadInicial: Int)
    : VehiculoTransporte(conductor, capacidadInicial) {

    override fun calcularDesgaste(): Double {
        return super.calcularDesgaste() * 1.5
    }

    fun aplicarDesgaste() {
        val desgaste = calcularDesgaste()
        println("Desgaste del bus articulado: $desgaste")
    }
}

fun main() {
    val bus = VehiculoTransporte("Carlos Perez", 0)

    bus.abordarPasajeros(30)
    bus.bajarPasajeros(10)
    bus.bajarPasajeros(50)

    println(bus.conductor)
    println(bus.consultarOcupacion())

    println("---- Bus Articulado ----")

    val articulado = BusArticulado("Maria Lopez", 0)
    articulado.aplicarDesgaste()
    println(articulado.consultarOcupacion())
}
