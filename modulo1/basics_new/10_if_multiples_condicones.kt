fun main() {
    println("if con multiples condiciones")
    val sistolica = readln()?.toIntOrNull() ?: 0 
    val clasificacion = if (sistolica < 90) {
        "Hipotension"
    } else if (sistolica <= 119) {
        "Normal"
    } else if (sistolica <= 139) { 
        "Elevada"
    } else if (sistolica <= 179) {
        "Hipertencion grado 2"
    } else {
        "Crisis Hipertensiva"
    }
    print("Clasificacion: $clasificacion")
}