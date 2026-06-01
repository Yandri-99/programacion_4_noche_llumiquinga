
fun main() {
    println("Control de flujo")
    println("If Dos Caminos - Descuento Pasaje")
    println("Tiene tarjeta de descuento? s/n")
    val tieneDescuento = readLine()?.trim()?.lowercase() == "s"
    println("Valor del pasaje base? $")
    val pasajeBase = readLine()?.toDoubleOrNull() ?: 0.0
    if (tieneDescuento) {
        val descuento = pasajeBase * 0.50
        println("Descuento aplicado $$descuento Pasajero paga ${pasajeBase - descuento}")
    } else {
        println("Pasajero paga $$pasajeBase")
    }
}
