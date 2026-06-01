
data class TipoBus(val id: Int, val nombre: String)

data class Vehiculo(
    val id: Int,
    val placa: String,
    val capacidad: Int,
    val kilometraje: Int,
    val tipo: TipoBus,
    val activo: Boolean = true
) {
    val disponible: Boolean get() = activo && kilometraje < 100000
    val costoPorKm: Double get() = when (tipo.id) {
        1 -> 2.5
        2 -> 3.0
        3 -> 1.8
        else -> 2.0
    }

    fun aplicarMantenimiento(kmRecorridos: Int): Vehiculo {
        require(kmRecorridos in 0..10000) { "Km debe estar entre 0 y 10000" }
        return copy(kilometraje = kilometraje + kmRecorridos)
    }
}

object FlotaTransporte {
    private val tipos = mutableListOf(
        TipoBus(1, "Bus Urbano"),
        TipoBus(2, "Bus Articulado"),
        TipoBus(3, "Microbus")
    )
    private val vehiculos = mutableListOf<Vehiculo>()
    private var siguienteId = 1

    fun agregarVehiculo(placa: String, capacidad: Int, kilometraje: Int, tipoId: Int): Vehiculo? {
        val tipo = tipos.find { it.id == tipoId } ?: return null
        val vehiculo = Vehiculo(siguienteId++, placa, capacidad, kilometraje, tipo)
        vehiculos.add(vehiculo)
        return vehiculo
    }

    fun listar(): List<Vehiculo> = vehiculos.toList()
    fun disponibles(): List<Vehiculo> = vehiculos.filter { it.disponible }
    fun porTipo(id: Int): List<Vehiculo> = vehiculos.filter { it.tipo.id == id }
    fun buscar(query: String): List<Vehiculo> =
        vehiculos.filter { it.placa.contains(query, ignoreCase = true) }
}

fun main() {
    FlotaTransporte.agregarVehiculo("PAB-1234", 45, 25000, 1)
    FlotaTransporte.agregarVehiculo("XYZ-5678", 60, 95000, 2)
    FlotaTransporte.agregarVehiculo("ABC-9012", 25, 15000, 3)
    FlotaTransporte.agregarVehiculo("DEF-3456", 50, 30000, 1)
    FlotaTransporte.agregarVehiculo("GHI-7890", 40, 12000, 3)

    println("=== Todos los vehiculos ===")
    FlotaTransporte.listar().forEach { v ->
        val estado = if (v.disponible) "Disponible" else "No disponible"
        println("$estado ${v.placa} — ${v.tipo.nombre} (${v.capacidad} pasajeros)")
    }

    println("\n=== Disponibles con mantenimiento programado ===")
    FlotaTransporte.disponibles()
        .map { it.aplicarMantenimiento(500) }
        .forEach { println("  ${it.placa}: ${it.kilometraje} km") }

    for (vehiculo in FlotaTransporte.listar()) {
        println("${vehiculo.placa}")
    }
}
