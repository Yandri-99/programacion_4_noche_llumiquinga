
object ConfiguracionFlota {
    val empresa: String = "Transporte Urbano S.A."
    val capacidadMax: Int = 60
    private val apiKey: String = "tsp-secreto-456"

    fun baseUrl() = "https://api.$empresa:8080"
    fun headers() = mapOf("Authorization" to "Bearer $apiKey")
}

class Conductor private constructor(val id: Int, val nombre: String) {
    companion object {
        private var contadorId = 0

        fun crear(nombre: String, licencia: String): Conductor? {
            if (nombre.isBlank() || !licencia.contains("-")) return null
            return Conductor(++contadorId, nombre.trim())
        }

        const val ROL_DEFECTO = "conductor"
    }
}

fun main() {
    println(ConfiguracionFlota.baseUrl())

    val c = Conductor.crear("Ana", "LIC-001")
    println(c)
}
