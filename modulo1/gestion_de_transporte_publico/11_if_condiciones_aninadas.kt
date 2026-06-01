
fun main() {
    println("If con condiciones anidadas - Estado del Bus")
    println("El bus tiene mantenimiento preventivo al dia? s/n")
    val tieneMantenimiento = readLine()?.trim()?.lowercase() == "s"
    println("Kilometraje del bus:")
    val kilometraje = readLine()?.toIntOrNull() ?: 0
    if (tieneMantenimiento) {
        println("Bus con mantenimiento al dia")
        if (kilometraje > 50000) {
            println("Programar cambio de llantas")
        } else if (kilometraje > 30000) {
            println("Programar revision de frenos")
        } else {
            println("Kilometraje dentro del rango seguro")
        }
    } else {
        println("Bus sin mantenimiento al dia")
        if (kilometraje > 20000 || kilometraje < 0) {
            println("Requiere mantenimiento urgente")
        } else {
            println("Programar mantenimiento preventivo")
        }
    }
}
