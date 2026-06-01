
enum class EstadoBus(val descripcion: String, val esTerminal: Boolean) {
    EN_RUTA("Realizando recorrido", false),
    EN_PARADA("Detenido en parada", false),
    COMPLETADO("Recorrido finalizado", true),
    AVERIADO("Fuera de servicio por falla", true),
    CANCELADO("Recorrido cancelado", true);

    fun puedeTransicionarA(siguiente: EstadoBus): Boolean = when (this) {
        EN_RUTA -> siguiente == EN_PARADA || siguiente == AVERIADO
        EN_PARADA -> siguiente == EN_RUTA || siguiente == COMPLETADO || siguiente == CANCELADO
        else -> false
    }
}

fun main() {
    val estado = EstadoBus.EN_RUTA
    println(estado.descripcion)
    println(estado.esTerminal)

    val icono = when (estado) {
        EstadoBus.EN_RUTA -> "Bus"
        EstadoBus.EN_PARADA -> "Detenido"
        EstadoBus.COMPLETADO -> "Finalizado"
        EstadoBus.AVERIADO -> "Falla"
        EstadoBus.CANCELADO -> "Cancelado"
    }
    println(icono)

    println(estado.puedeTransicionarA(EstadoBus.EN_PARADA))
}
