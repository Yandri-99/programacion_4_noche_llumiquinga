
fun main() {
    println("Set - Flotas de Buses")
    println("Inmutables")
    val numerosBus = setOf(101, 102, 103, 104, 105, 104, 103, 102, 101)
    println(numerosBus)
    println("Operaciones de Conjuntos")

    val rutasNorte = setOf(101, 103, 105, 107, 109)
    println("rutasNorte $rutasNorte")

    val rutasSur = setOf(102, 104, 106, 108, 110)
    val rutasExpreso = setOf(105, 110, 115, 120)
    println("rutasNorte $rutasNorte")
    println("union ${rutasNorte union rutasSur}")
    println("interaccion ${rutasExpreso intersect rutasNorte}")
    println("interaccion ${rutasExpreso intersect rutasSur}")
    println("substraccion ${rutasNorte subtract rutasSur}")
    println("rutasNorte $rutasNorte")
    println("rutasSur $rutasSur")

    println("Set mutables")
    val paradasAutorizadas = mutableSetOf("Terminal", "Plaza", "Mercado", "Hospital")
    println(paradasAutorizadas)
    paradasAutorizadas.add("Terminal")
    println(paradasAutorizadas)
    paradasAutorizadas.add("Universidad")
    println(paradasAutorizadas)
    paradasAutorizadas.remove("Mercado")
    println(paradasAutorizadas)
    println("Verificar si un valor existe ${"Terminal" in paradasAutorizadas}")
    println("Verificar si un valor existe ${"Mercado" in paradasAutorizadas}")
}
