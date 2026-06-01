
fun main() {
    println("CICLOS repeat - Inspeccion de Neumaticos")

    println("Cuantos neumaticos revisar:")
    val revisiones = readLine()?.toIntOrNull() ?: 4

    var presionTotal = 0

    repeat(revisiones) { i ->
        println("Neumatico ${i + 1} (presion en PSI):")
        val presion = readLine()?.toIntOrNull() ?: 0
        presionTotal += presion
    }

    val promedio = presionTotal / revisiones

    println("Presion promedio: $promedio PSI")

    println("Estado: ${
        when {
            promedio < 30 -> "Baja - inflar neumaticos"
            promedio <= 40 -> "Normal"
            else -> "Alta - revisar presion"
        }
    }")
}
