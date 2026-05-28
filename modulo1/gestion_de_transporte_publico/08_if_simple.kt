
fun main() {
    println("Control de flujo")
    println("If Simple - Limite de Velocidad")
    println("Velocidad del bus (km/h):")
    val velocidad = readLine()?.toDoubleOrNull() ?: 35.0
    if (velocidad > 60) {
        println("Alerta: Exceso de velocidad - reducir velocidad")
    }
    if (velocidad <= 60) {
        println("Velocidad dentro del limite")
    }
    println("Velocidad registrada: $velocidad km/h")
}
