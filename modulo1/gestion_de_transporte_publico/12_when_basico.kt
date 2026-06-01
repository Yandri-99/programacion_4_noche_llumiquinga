
fun main() {
    println("Condicional when - Tipo de Transporte")
    println("Codigo de tipo (1-7):")
    val codigo = readLine()?.toIntOrNull() ?: 0
    val tipoTransporte = when (codigo) {
        1 -> "Bus Urbano"
        2 -> "Bus Interprovincial"
        3 -> "Metro"
        4 -> "Tranvia"
        5 -> "Taxi"
        6 -> "Bicicleta Publica"
        7 -> "Trolebus"
        else -> "Tipo no registrado"
    }
    println("Tipo de Transporte: $tipoTransporte")
}
