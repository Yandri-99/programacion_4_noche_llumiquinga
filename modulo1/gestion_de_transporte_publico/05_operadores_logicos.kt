
fun main() {
    println("Operadores Logicos - Control de Salida")
    val tieneConductor = true
    val tanqueLleno = false
    val revisionTecnica = true

    println("operador And &&")
    println("$tieneConductor && $tanqueLleno = ${tieneConductor && tanqueLleno}")
    println("$tieneConductor && $revisionTecnica = ${tieneConductor && revisionTecnica}")

    println("Or Logico ||")
    println("$tieneConductor || $tanqueLleno = ${tieneConductor || tanqueLleno}")
    println("$tieneConductor || $revisionTecnica = ${tieneConductor || revisionTecnica}")
    println("$tieneConductor || $tanqueLleno = ${tieneConductor || tanqueLleno}")
    println("$tieneConductor || $tanqueLleno || $revisionTecnica = ${tieneConductor || tanqueLleno || revisionTecnica}")

    println("Not Logico !")
    println("! $tieneConductor = ${!tieneConductor}")
    println("! $revisionTecnica = ${!revisionTecnica}")
    val texto = readLine()
    println(texto)
}
