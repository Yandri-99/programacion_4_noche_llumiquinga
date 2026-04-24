data class Categoria(val id: Int, val nombre: String)

data class Producto(
    val id:        Int,
    val nombre:    String,
    val precio:    Double,
    val stock:     Int,
    val categoria: Categoria,
    val activo:    Boolean = true
) {
    // ABSTRACCIÓN: el usuario consulta disponible sin saber la lógica
    val disponible: Boolean get() = activo && stock > 0
    val precioConIva: Double get() = precio * 1.19

    // Devuelve una copia — inmutabilidad como forma de encapsulamiento
    fun aplicarDescuento(porcentaje: Double): Producto {
        require(porcentaje in 0.0..100.0) { "Descuento debe ser entre 0 y 100" }
        return copy(precio = precio * (1 - porcentaje / 100))
    }
}

// ENCAPSULAMIENTO: el estado del catálogo es privado y mutable internamente
object CatalogoProductos {
    private val categorias = mutableListOf(
        Categoria(1, "Periféricos"),
        Categoria(2, "Pantallas"),
        Categoria(3, "Audio")
    )
    private val productos   = mutableListOf<Producto>()
    private var siguienteId = 1

    fun agregarProducto(nombre: String, precio: Double, stock: Int, categoriaId: Int): Producto? {
        val categoria = categorias.find { it.id == categoriaId } ?: return null
        val producto  = Producto(siguienteId++, nombre, precio, stock, categoria)
        productos.add(producto)
        return producto
    }

    // ABSTRACCIÓN: interfaz pública limpia — solo lectura de listas
    fun listar(): List<Producto>              = productos.toList()
    fun disponibles(): List<Producto>         = productos.filter { it.disponible }
    fun porCategoria(id: Int): List<Producto> = productos.filter { it.categoria.id == id }
    fun buscar(query: String): List<Producto> =
        productos.filter { it.nombre.contains(query, ignoreCase = true) }
}

fun main() {
    CatalogoProductos.agregarProducto("Teclado mecánico",   89.99, 15, 1)
    CatalogoProductos.agregarProducto("Mouse inalámbrico",  29.99,  0, 1)
    CatalogoProductos.agregarProducto("Monitor 27\"",      349.99,  5, 2)
    CatalogoProductos.agregarProducto("Auriculares BT",    149.99,  8, 3)
    CatalogoProductos.agregarProducto("Celular Motorola G85", 220.00, 4, 1)
    CatalogoProductos.agregarProducto("Audifonos Redmi", 40.00, 2, 2)

    println("=== Todos los productos ===")
    CatalogoProductos.listar().forEach { p ->
        val estado = if (p.disponible) "✅" else "❌"
        println("$estado ${p.nombre} — ${"%.2f".format(p.precioConIva)} (con IVA)")
    }

    println("\n=== Disponibles con 10% descuento ===")
    CatalogoProductos.disponibles()
        .map { it.aplicarDescuento(15.0) }
        .forEach { println("  ${it.nombre}: ${"%.2f".format(it.precio)}") }
    
    for (producto in CatalogoProductos.listar()) {
        println("${producto.nombre}")
    }
}
