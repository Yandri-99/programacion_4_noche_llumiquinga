
fun main() {
    println("Ciclos while - Control de Pasajeros")
    println("while basico")
    var contador = 1
    while (contador <= 5) {
        println("Pasajero $contador aborda")
        contador++
    }

    contador = 1
    do {
        println("Revisando pasajero $contador")
        contador++
    } while (contador <= 5)

    println("break continue")
    contador = 1
    while (contador <= 10) {
        contador++
        if (contador == 3) continue
        if (contador == 7) break
        println("Bus procesando parada $contador")
    }
    var input: String
    while (true) {
        println("Escribe 'salir' para finalizar ruta:")
        input = readLine() ?: ""
        if (input == "salir") break
        println("Ingresaste $input")
    }
}
