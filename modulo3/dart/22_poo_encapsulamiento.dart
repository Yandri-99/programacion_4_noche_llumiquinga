class CuentaBancaria {
  final String titular;
  double _saldo;  // privado — nadie lo modifica directamente

  CuentaBancaria(this.titular, double saldoInicial)
      : _saldo = saldoInicial;

  // Getter — lectura permitida, escritura no
  double get saldo => _saldo;

  // Los únicos caminos para modificar _saldo
  void depositar(double monto) {
    if (monto <= 0) throw ArgumentError('El monto debe ser positivo');
    _saldo += monto;
    print('Depósito de \$$monto. Nuevo saldo: \$$_saldo');
  }

  void retirar(double monto) {
    if (monto <= 0)      throw ArgumentError('El monto debe ser positivo');
    if (monto > _saldo)  throw StateError('Saldo insuficiente');
    _saldo -= monto;
    print('Retiro de \$$monto. Nuevo saldo: \$$_saldo');
  }
}

void main() {
  final cuenta = CuentaBancaria('Ana López', 500.0);

  cuenta.depositar(200.0);  // Depósito de $200.0. Nuevo saldo: $700.0
  cuenta.retirar(150.0);    // Retiro de $150.0.  Nuevo saldo: $550.0
  print(cuenta.saldo);      // 550.0

  // cuenta._saldo = 999999;  // ERROR — privado, Dart no lo permite
}