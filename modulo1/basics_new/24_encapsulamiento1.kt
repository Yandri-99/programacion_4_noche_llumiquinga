open class CuentaBancaria(titular: String, saldoInicial: Double) {

    val titular: String = titular

    private var saldo: Double = saldoInicial

    internal val numeroCuenta: String =
        "ES${(100000..999999).random()}"

    protected open fun calcularInteres(): Double = saldo * 0.02

    fun depositar(monto: Double) {
        require(monto > 0) { "El monto debe ser positivo" }
        saldo += monto
        println("Depositado: $${"%.2f".format(monto)} | Nuevo saldo: ${consultarSaldo()}")
    }

    fun retirar(monto: Double): Boolean {
        require(monto > 0) { "El monto debe ser positivo" }
        if (monto > saldo) {
            println("Fondos insuficientes")
            return false
        }
        saldo -= monto
        println("Retirado: $${"%.2f".format(monto)} | Nuevo saldo: ${consultarSaldo()}")
        return true
    }

    fun consultarSaldo(): String = "$${"%.2f".format(saldo)}"
}

// Subclase para aprovechar protected + override
class CuentaAhorros(titular: String, saldoInicial: Double)
    : CuentaBancaria(titular, saldoInicial) {

    override fun calcularInteres(): Double {
        return super.calcularInteres() * 1.5
    }

    fun aplicarInteres() {
        val interes = calcularInteres()
        depositar(interes)
    }
}

fun main() {
    val cuenta = CuentaBancaria("Ana García", 1000.0)

    cuenta.depositar(500.0)
    cuenta.retirar(200.0)
    cuenta.retirar(2000.0)

    println(cuenta.titular)
    println(cuenta.consultarSaldo())

    println("---- Cuenta Ahorros ----")

    val ahorros = CuentaAhorros("Juan Pérez", 2000.0)
    ahorros.aplicarInteres()
    println(ahorros.consultarSaldo())
}
