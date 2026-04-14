fun main() {
    println("When con condiciones")
    println("Edad del paciente:")
    val edad = readLine()?.toIntOrNull() ?: 0
    println("¿Tiene Seguro? s/n:")
    val tieneSeguro = readLine()?.trim()?.lowercase() == "s"
    val nivelSeguro = if (tieneSeguro) {
        println("Nivel de seguro (BASICO/INTERMEDIO/PREMIUM):")
        readLine()?.trim()?.uppercase() ?: "" 
    } else {
        ""
    }
    val copago = when {
        !tieneSeguro && edad < 18 -> 0.0
        !tieneSeguro && edad >= 65 -> 15.0
        !tieneSeguro -> 45.0
        nivelSeguro == "BASICO" -> 20.0
        nivelSeguro == "INTERMEDIO" -> 10.0
        nivelSeguro == "PREMIUM" -> 0.0
        else -> 50.0 
    }
    println("Copago aplicado: $ ${"%.2f".format(copago)}")
}