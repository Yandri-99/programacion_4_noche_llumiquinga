class Rectangulo(val ancho: Double, val alto: Double) {
    val area:      Double get() = ancho * alto
    val perimetro: Double get() = 2 * (ancho + alto)

    // Siempre llaman al constructor primario con this(...)
    constructor(lado: Double) : this(lado, lado)
    constructor(ancho: Int, alto: Int) : this(ancho.toDouble(), alto.toDouble())

    override fun toString() = "Rectángulo(${ancho}x${alto}) | área=${area}"
}

fun main() {
    val r1 = Rectangulo(5.0, 3.0)
    val r2 = Rectangulo(4.0)        // cuadrado
    val r3 = Rectangulo(6, 2)       // con Int

    println(r1)  // Rectángulo(5.0x3.0) | área=15.0
    println(r2)  // Rectángulo(4.0x4.0) | área=16.0
}