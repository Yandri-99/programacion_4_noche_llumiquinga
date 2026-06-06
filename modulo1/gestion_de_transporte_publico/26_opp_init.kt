
class Ruta(val codigo: String, val nombre: String) {
    val nombreNormalizado: String
    val zonaRuta: String

    init {
        require(codigo.isNotBlank()) { "El codigo de ruta no puede estar vacio" }
        require(nombre.contains("-")) { "Nombre de ruta invalido: $nombre" }

        nombreNormalizado = nombre.trim().lowercase()
        zonaRuta = nombre.substringAfter("-")
    }
}

fun main() {
    val r = Ruta("R-42", "  Norte-Central  ")
    println(r.nombreNormalizado)
    println(r.zonaRuta)
}
