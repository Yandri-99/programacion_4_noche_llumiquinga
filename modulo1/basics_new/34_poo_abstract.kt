abstract class Figura(val nombre: String) {
    // abstract — las subclases DEBEN implementar esto (herencia forzada)
    abstract val area: Double
    abstract val perimetro: Double
    abstract fun descripcion(): String

    // concreto — disponible en todas las subclases (reutilización)
    fun comparar(otra: Figura): String = when {
        area > otra.area -> "$nombre es más grande que ${otra.nombre}"
        area < otra.area -> "$nombre es más pequeña que ${otra.nombre}"
        else             -> "$nombre y ${otra.nombre} tienen la misma área"
    }

    // Polimorfismo: toString usa area y descripcion que son polimórficas
    override fun toString() = "${descripcion()} | Área: ${"%.2f".format(area)}"
}

class Circulo(val radio: Double) : Figura("Círculo") {
    override val area:       Double get() = Math.PI * radio * radio
    override val perimetro:  Double get() = 2 * Math.PI * radio
    override fun descripcion() = "Círculo de radio $radio"
}

class Rectangulo(val ancho: Double, val alto: Double) : Figura("Rectángulo") {
    override val area:       Double get() = ancho * alto
    override val perimetro:  Double get() = 2 * (ancho + alto)
    override fun descripcion() = "Rectángulo de ${ancho}x${alto}"
}

class TrianguloEquilatero(val lado: Double) : Figura("Triángulo") {
    override val area:       Double get() = (Math.sqrt(3.0) / 4) * lado * lado
    override val perimetro:  Double get() = 3 * lado
    override fun descripcion() = "Triángulo equilátero de lado $lado"
}

fun main() {
    // POLIMORFISMO: la lista acepta cualquier Figura
    val figuras: List<Figura> = listOf(
        Circulo(5.0),
        Rectangulo(4.0, 6.0),
        TrianguloEquilatero(8.0)
    )

    figuras.forEach { println(it) }  // toString polimórfico

    val mayor = figuras.maxByOrNull { it.area }
    println("\nFigura más grande: ${mayor?.nombre}")

    println(figuras[0].comparar(figuras[1]))
}