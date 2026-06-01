
fun main() {
    println("if con multiples condiciones - Nivel de Ocupacion")
    val ocupacion = readln()?.toIntOrNull() ?: 0
    val clasificacion = if (ocupacion < 20) {
        "Baja ocupacion"
    } else if (ocupacion <= 40) {
        "Ocupacion media"
    } else if (ocupacion <= 50) {
        "Alta ocupacion"
    } else {
        "Sobrecupo - Revisar normativa"
    }
    print("Clasificacion: $clasificacion")
}
