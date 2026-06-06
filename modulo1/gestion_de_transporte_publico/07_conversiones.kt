
fun main() {
    println("Conversiones en Transporte")
    val distanciaKm: Int = 25

    val distanciaMillas: Double = distanciaKm.toDouble() * 0.621371
    val distanciaMetros: Long = distanciaKm.toLong() * 1000
    val distanciaStr: String = distanciaKm.toString()

    println("to Millas $distanciaMillas")
    println("to Metros $distanciaMetros")
    println("to String $distanciaStr")

    println("String a Numerico")
    val tarifa = "0.75".toDouble()
    val paradas = "12".toInt()

    val invalido = "abc".toIntOrNull()
    println(invalido)
}
