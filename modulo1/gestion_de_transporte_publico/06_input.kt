
fun main() {
    println("Registro de Viaje")
    println("Ingrese nombre del conductor:")
    val conductor = readLine() ?: "Anonimo"
    println("Conductor registrado: $conductor")

    println("Ingrese numero de pasajeros:")
    val pasajerosStr = readLine()
    println("Pasajeros ingresados: $pasajerosStr")
    val pasajerosNum = pasajerosStr?.toDouble()
    print("El doble de pasajeros es: ${pasajerosNum!! * 2}")
}
