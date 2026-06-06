
fun main() {
    println("When con condiciones - Tarifa de Transporte")
    println("Edad del pasajero:")
    val edad = readLine()?.toIntOrNull() ?: 0
    println("¿Tiene tarjeta ciudad? s/n:")
    val tieneTarjeta = readLine()?.trim()?.lowercase() == "s"
    val tipoTarjeta = if (tieneTarjeta) {
        println("Tipo de tarjeta (ESTUDIANTE/ADULTO/ADULTO_MAYOR):")
        readLine()?.trim()?.uppercase() ?: ""
    } else {
        ""
    }
    val tarifa = when {
        !tieneTarjeta && edad < 5 -> 0.0
        !tieneTarjeta && edad >= 65 -> 0.25
        !tieneTarjeta -> 0.75
        tipoTarjeta == "ESTUDIANTE" -> 0.30
        tipoTarjeta == "ADULTO" -> 0.75
        tipoTarjeta == "ADULTO_MAYOR" -> 0.0
        else -> 0.75
    }
    println("Tarifa aplicada: $ ${"%.2f".format(tarifa)}")
}
