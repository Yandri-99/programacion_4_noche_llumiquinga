


interface PagableTransporte {
    fun procesar(monto: Double): Boolean
    val nombre: String
}

class TarjetaTransporte(val codigo: String) : PagableTransporte {
    override val nombre = "Tarjeta de Transporte"
    override fun procesar(monto: Double): Boolean {
        println("Cobrando $${"%.2f".format(monto)} a tarjeta $codigo")
        return true
    }
}

class EfectivoTransporte : PagableTransporte {
    override val nombre = "Efectivo"
    override fun procesar(monto: Double): Boolean {
        println("Recibiendo $${"%.2f".format(monto)} en efectivo")
        return true
    }
}

class AppPago(val telefono: String) : PagableTransporte {
    override val nombre = "App de Pago"
    override fun procesar(monto: Double): Boolean {
        println("Cobrando $${"%.2f".format(monto)} via app a $telefono")
        return true
    }
}

class BilleteraElectronica : PagableTransporte {
    override val nombre = "Billetera Electronica"
    override fun procesar(monto: Double): Boolean {
        println("Cobrando $${"%.2f".format(monto)} desde billetera electronica")
        return true
    }
}

fun cobrarPasaje(monto: Double, metodoPago: PagableTransporte) {
    println("Procesando pago con ${metodoPago.nombre}...")
    val exito = metodoPago.procesar(monto)
    println(if (exito) "Pago exitoso" else "Pago fallido")
}

fun main() {
    val metodos: List<PagableTransporte> = listOf(
        TarjetaTransporte("1234-5678-9012"),
        AppPago("099-123-4567"),
        EfectivoTransporte(),
        BilleteraElectronica()
    )

    metodos.forEach { cobrarPasaje(0.75, it) }
}
