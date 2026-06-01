class TarjetaTransporte {
  final String numeroTarjeta;
  double _saldo;

  TarjetaTransporte(this.numeroTarjeta, double saldoInicial)
      : _saldo = saldoInicial;

  double get saldo => _saldo;

  void recargar(double monto) {
    if (monto <= 0) throw ArgumentError('El monto debe ser positivo');
    _saldo += monto;
    print('Recarga de \$$monto. Nuevo saldo: \$$_saldo');
  }

  void pagarPasaje(double tarifa) {
    if (tarifa <= 0)      throw ArgumentError('La tarifa debe ser positiva');
    if (tarifa > _saldo)  throw StateError('Saldo insuficiente');
    _saldo -= tarifa;
    print('Pasaje pagado: \$$tarifa. Nuevo saldo: \$$_saldo');
  }
}

void main() {
  final tarjeta = TarjetaTransporte('101-234-567', 5.00);

  tarjeta.recargar(10.00);
  tarjeta.pagarPasaje(0.35);
  print(tarjeta.saldo);
}
