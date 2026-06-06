
interface Mantenible {
    val id: String
    fun registrarMantenimiento(): String
    val version: Int get() = 1
}

interface Asegurable {
    val errores: List<String>
    val esValido: Boolean get() = errores.isEmpty()

    fun validar(): Boolean
    fun imprimirErrores() {
        if (errores.isEmpty()) println("Sin errores")
        else errores.forEach { println("  Error: $it") }
    }
}

data class VehiculoAsegurado(
    override val id: String,
    val placa: String,
    val conductor: String,
    val primaSeguro: Double
) : Mantenible, Asegurable {

    override fun registrarMantenimiento() =
        "$id|$placa|$conductor|$primaSeguro"

    override val errores: List<String> get() = buildList {
        if (placa.isBlank()) add("La placa no puede estar vacia")
        if (conductor.isBlank()) add("El conductor no puede estar vacio")
        if (primaSeguro <= 0) add("La prima debe ser mayor que cero")
    }

    override fun validar() = esValido
}

fun main() {
    val v1 = VehiculoAsegurado("V001", "PAB-1234", "Carlos", 1500.0)
    val v2 = VehiculoAsegurado("V002", "", "", -5.0)

    fun procesarMantenible(m: Mantenible) = println("-> ${m.registrarMantenimiento()}")
    fun procesarAsegurable(a: Asegurable) {
        println("Valido: ${a.esValido}")
        a.imprimirErrores()
    }

    procesarMantenible(v1)
    procesarAsegurable(v1)
    procesarAsegurable(v2)
}
