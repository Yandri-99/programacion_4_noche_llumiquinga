
fun main() {
    println("CICLOS for - Recorrido de Paradas")
    for (i in 1..5) {
        println("Parada $i")
    }
    println("until")
    for (i in 1 until 5) {
        println("Parada $i")
    }
    println("downTo")
    for (i in 10 downTo 1) {
        println("Parada $i")
    }
    println("listas")
    val paradas = listOf("Terminal Norte", "Plaza Central", "Mercado", "Hospital", "Terminal Sur")
    for (parada in paradas) {
        print(parada)
    }
    println("indice valor")
    for ((index, valor) in paradas.withIndex()) {
        println("$index: $valor")
    }
}
