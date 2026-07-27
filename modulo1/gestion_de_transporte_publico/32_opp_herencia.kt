
class VehiculoSimple(val nombre: String)

open class VehiculoTransporte(val nombre: String, val sonido: String) {
    open fun hacerSonido() = println("$nombre hace: $sonido")
    open fun descripcion() = "Soy $nombre"

    fun moverse() = println("$nombre se esta moviendo")
}

class Bus(nombre: String) : VehiculoTransporte(nombre, "Bip Bip") {
    override fun hacerSonido() {
        super.hacerSonido()
        println("(abre puertas)")
    }
    override fun descripcion() = "${super.descripcion()}, un bus urbano"
}

class Taxi(nombre: String, val tieneTaximetro: Boolean) : VehiculoTransporte(nombre, "Honk") {
    override fun descripcion() =
        "${super.descripcion()}, un taxi ${if (tieneTaximetro) "con taximetro" else "sin taximetro"}"
}

fun main() {
    val bus = Bus("Bus Ruta 101")
    bus.hacerSonido()

    val taxi = Taxi("Taxi Express", true)
    println(taxi.descripcion())

    bus.moverse()
}
