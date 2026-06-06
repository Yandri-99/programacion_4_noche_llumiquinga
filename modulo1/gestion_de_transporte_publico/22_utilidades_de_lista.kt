
fun main() {
    println("Utilidades de listas - Flota de Buses")
    val capacidades = listOf(30, 45, 50, 60, 25, 55, 40, 35)
    println(capacidades)
    val capacidadesDobles = capacidades.map { it * 2 }
    println(capacidadesDobles)
    val capacidadesTexto = capacidades.map { "Cap$it" }
    println(capacidadesTexto)

    println("Filter")
    val busesGrandes = capacidades.filter { it >= 50 }
    println(busesGrandes)
    val busesMas40 = capacidades.filter { it > 40 }
    println(busesMas40)
    val busesEntre40y55 = capacidades.filter { it >= 40 && it <= 55 }
    println(busesEntre40y55)
    val busesPequenos = capacidades.filterNot { it >= 50 }
    println(busesPequenos)
}
