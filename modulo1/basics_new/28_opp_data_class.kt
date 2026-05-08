data class Producto(
    val id:        Int,
    val nombre:    String,
    val precio:    Double,
    val categoria: String,
    val activo:    Boolean = true
)

fun main() {
    val p1 = Producto(1, "Teclado mecánico", 89.99, "Periféricos")
    val p2 = Producto(2, "Teclado mecánico", 89.99, "Periféricos")
    val p3 = Producto(3, "Monitor 27\"",     349.99, "Pantallas")

    // toString() automático
    println(p1)  // Producto(id=1, nombre=Teclado mecánico, ...)

    // equals() por valor
    println(p1 == p2)   // true
    println(p1 == p3)   // false

    // copy() — nuevo objeto con cambios puntuales
    val barato   = p1.copy(precio = 59.99)
    val inactivo = p1.copy(activo = false)

    // Desestructuración
    val (id, nombre, precio) = p1
    println("$id: $nombre — $$precio")

    // En bucles
    listOf(p1, p3).forEach { (id2, nombre2, precio2) ->
        println("[$id2] $nombre2: $$precio2")
    }
}