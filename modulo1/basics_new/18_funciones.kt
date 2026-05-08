fun main() {
    saludar()
    saludarConParametros("Michael")
    val numero1 = 10
    val numero2 = 20
    println("Suma de $numero1 + $numero2 = ${sumar(numero1, numero2)}")
    println("Resta de $numero1 - $numero2 = ${restar(numero1, numero2)}")
    operacion()
    println("Multiplicar de $numero1 * $numero2 = ${multiplicar(numero1, numero2)}")
}

fun saludar() {
    println("hello world from functions")
}

fun saludarConParametros(nombre: String) {
    println("Buenas Noches: $nombre")
}

fun sumar(numero1: Int, numero2: Int): Int {
    return numero1 + numero2
}

// función simplificada
fun restar(numero1: Int, numero2: Int) = numero1 - numero2

// función dentro de función
fun operacion() {
    fun cuadrado(x: Int) = x * x
    println("Cuadrado de 5: ${cuadrado(5)}")
}
//Funciones
val multiplicar = { a: Int, b: Int -> a * b }