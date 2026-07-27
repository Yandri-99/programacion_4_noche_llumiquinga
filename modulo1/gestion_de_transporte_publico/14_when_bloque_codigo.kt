
fun main() {
    println("When con bloques de codigo - Nivel de Incidente")

    println("Nombre del bus:")
    val bus = readLine()?.trim() ?: ""

    println("Nivel de incidente CRITICO/GRAVE/MODERADO/LEVE")
    val nivel = readLine()?.trim()?.uppercase() ?: ""

    when (nivel) {
        "CRITICO" -> {
            println("ALERTA CRITICA - Bus: $bus")
            println("Evacuar pasajeros inmediatamente")
            println("Notificar a emergencias")
        }
        "GRAVE" -> {
            println("GRAVE - Bus: $bus")
            println("Detener el bus y reportar a central")
            println("Evaluar en 5 minutos")
        }
        "MODERADO" ->
            println("Moderado - Bus: $bus, reportar y continuar monitoreando")

        "LEVE" ->
            println("Leve - Bus: $bus, registrar en bitacora")

        else ->
            println("Nivel de incidente no reconocido")
    }
    println("Numero de paradas:")
    val paradas = readLine()?.toIntOrNull() ?: 0
    val categoriaRuta = when (paradas) {
        in 0..5 -> "Ruta corta"
        in 6..12 -> "Ruta media"
        in 13..20 -> "Ruta larga"
        else -> "Ruta expreso"
    }
    println("$paradas paradas -> $categoriaRuta")
}
