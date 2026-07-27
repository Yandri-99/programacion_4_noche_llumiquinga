
fun main() {
    println("Funcion Lambda - Calculos de Transporte")
    val sumaPasajeros: (Int, Int) -> Int = { a: Int, b: Int -> a + b }
    println(sumaPasajeros(25, 18))

    val sumaPasajeros2: (Int, Int) -> Int = { a, b -> a + b }
    println(sumaPasajeros2(25, 18))

    val duplicarCapacidad: (Int) -> Int = { it * 2 }
    println(duplicarCapacidad(30))
}
