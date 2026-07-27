
fun main() {
    mostrarBienvenida()
    saludarConductor("Maria")
    val distancia = 120
    val tiempo = 2
    println("Velocidad promedio de $distancia km en $tiempo h = ${calcularVelocidad(distancia, tiempo)} km/h")
    println("Combustible gastado de $distancia km = ${calcularCombustible(distancia, tiempo)} litros")
    operacion()
    println("Costo total de $distancia km = ${calcularCosto(distancia)}")
}

fun mostrarBienvenida() {
    println("Bienvenido al Sistema de Transporte")
}

fun saludarConductor(nombre: String) {
    println("Buen viaje conductor: $nombre")
}

fun calcularVelocidad(distancia: Int, tiempo: Int): Int {
    return distancia / tiempo
}

fun calcularCombustible(distancia: Int, consumo: Int) = distancia * consumo

fun operacion() {
    fun cuadrado(x: Int) = x * x
    println("Distancia al cuadrado de 15: ${cuadrado(15)}")
}

val calcularCosto = { distancia: Int -> distancia * 5 }
