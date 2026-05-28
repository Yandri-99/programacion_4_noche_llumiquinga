
data class Bus(
    val id: Int,
    val placa: String,
    val capacidad: Int,
    val tipo: String,
    val activo: Boolean = true
)

fun main() {
    val b1 = Bus(1, "PAB-1234", 45, "Urbano")
    val b2 = Bus(2, "PAB-1234", 45, "Urbano")
    val b3 = Bus(3, "XYZ-5678", 60, "Articulado")

    println(b1)

    println(b1 == b2)
    println(b1 == b3)

    val busPequeno = b1.copy(capacidad = 30)
    val busInactivo = b1.copy(activo = false)

    val (id, placa, capacidad) = b1
    println("$id: $placa — $capacidad pasajeros")

    listOf(b1, b3).forEach { (id2, placa2) ->
        println("[$id2] $placa2")
    }
}
