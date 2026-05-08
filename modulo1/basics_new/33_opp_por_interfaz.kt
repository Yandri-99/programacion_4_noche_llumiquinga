// La interfaz define el contrato — QUÉ puede hacer
// Las implementaciones definen el CÓMO
interface Pagable {
    fun procesar(monto: Double): Boolean
    val nombre: String
}

class TarjetaCredito(val numero: String) : Pagable {
    override val nombre = "Tarjeta de crédito"
    override fun procesar(monto: Double): Boolean {
        println("💳 Cargando $${"%.2f".format(monto)} a $numero")
        return true
    }
}

class PayPal(val email: String) : Pagable {
    override val nombre = "PayPal"
    override fun procesar(monto: Double): Boolean {
        println("🅿️ Enviando $${"%.2f".format(monto)} a $email")
        return true
    }
}

class Efectivo : Pagable {
    override val nombre = "Efectivo"
    override fun procesar(monto: Double): Boolean {
        println("💵 Recibiendo $${"%.2f".format(monto)} en efectivo")
        return true
    }
}

class Cheque : Pagable {
    override val nombre = "Cheque"
    override fun procesar(monto: Double): Boolean {
        println("💵 Recibiendo $${"%.2f".format(monto)} en cheque")
        return true
    }
}

// Esta función no sabe ni le importa qué tipo de pago es
// Solo sabe que recibe algo que implementa Pagable — POLIMORFISMO
fun cobrar(monto: Double, metodoPago: Pagable) {
    println("Procesando pago con ${metodoPago.nombre}...")
    val exito = metodoPago.procesar(monto)
    println(if (exito) "✅ Pago exitoso" else "❌ Pago fallido")
}

fun main() {
    val metodos: List<Pagable> = listOf(
        TarjetaCredito("**** **** **** 1234"),
        PayPal("ana@test.com"),
        Efectivo(),
        Cheque()
    )

    // Misma función — comportamiento distinto según el tipo
    metodos.forEach { cobrar(99.99, it) }
}