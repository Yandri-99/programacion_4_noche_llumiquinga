
fun main() {
    val nombreConductor = "Carlos"
    val edad: Int = 45
    var rutaAsignada = "Ruta 101"
    rutaAsignada = "Ruta 205"

    println("$nombreConductor conduce la $rutaAsignada")

    val numeroBus: Byte = 42
    val capacidad: Short = 50
    val pasajeros: Int = 1200
    val distanciaTotal: Long = 15_000_000

    println(numeroBus)
    println(capacidad)
    println(pasajeros)
    println(distanciaTotal)

    val tarifa: Float = 0.75f
    val recaudacion: Double = 1250.50

    val enServicio: Boolean = true

    val tipoBus: Char = 'A'
    val empresa: String = "Transporte Urbano S.A."
    val ciudad = "Quito"

    println("Tipo de ciudad: ${ciudad::class.simpleName}")

    val nombreParada = "terminal"
    val codigoRuta = "R-42"
    val nombreParadaMayuscula = nombreParada.uppercase()
    val codigoRutaMayuscula = codigoRuta.uppercase()

    println("Parada: ${nombreParadaMayuscula} - Ruta: ${codigoRutaMayuscula}")
    println("Parada: ${nombreParada.uppercase()} - Ruta: ${codigoRuta.uppercase()}")
}
