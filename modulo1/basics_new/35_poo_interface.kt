interface Serializable {
    val id: String                    // abstracta — debe implementarse
    fun serializar(): String          // abstracta — debe implementarse
    val version: Int get() = 1        // con default — puede sobreescribirse
}

interface Validable {
    val errores: List<String>
    val esValido: Boolean get() = errores.isEmpty()

    fun validar(): Boolean
    fun imprimirErrores() {                // implementación por defecto
        if (errores.isEmpty()) println("Sin errores")
        else errores.forEach { println("  ❌ $it") }
    }
}

// POLIMORFISMO: Pedido puede usarse donde se espere Serializable O Validable
data class Pedido(
    override val id: String,
    val cliente:     String,
    val items:       List<String>,
    val total:       Double
) : Serializable, Validable {

    override fun serializar() =
        "$id|$cliente|${items.joinToString(",")}|$total"

    override val errores: List<String> get() = buildList {
        if (cliente.isBlank()) add("El cliente no puede estar vacío")
        if (items.isEmpty())   add("El pedido debe tener al menos un item")
        if (total <= 0)        add("El total debe ser mayor que cero")
    }

    override fun validar() = esValido
}

fun main() {
    val pedido1 = Pedido("P001", "Ana", listOf("Teclado", "Mouse"), 119.98)
    val pedido2 = Pedido("P002", "",    emptyList(),                -5.0)

    // Polimorfismo por interfaz
    fun procesarSerializable(s: Serializable) = println("→ ${s.serializar()}")
    fun procesarValidable(v: Validable) {
        println("Válido: ${v.esValido}")
        v.imprimirErrores()
    }

    procesarSerializable(pedido1)   // → P001|Ana|Teclado,Mouse|119.98
    procesarValidable(pedido1)      // Válido: true / Sin errores
    procesarValidable(pedido2)      // Válido: false / ❌ ...
}