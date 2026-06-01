int Function(int) crearMultiplicadorTarifa(int factor) {
  return (int km) => km * factor;
}

void main() {
  final tarifaNormal = crearMultiplicadorTarifa(2);
  final tarifaPremium = crearMultiplicadorTarifa(3);
  final tarifaExpress = crearMultiplicadorTarifa(5);

  print(tarifaNormal(10));
  print(tarifaPremium(10));
  print(tarifaExpress(10));

  bool Function(int) crearValidadorCapacidad(int min, int max) {
    return (capacidad) => capacidad >= min && capacidad <= max;
  }

  final esMinibus = crearValidadorCapacidad(0, 30);
  final esBusEstandar = crearValidadorCapacidad(40, 80);

  print(esMinibus(25));
  print(esBusEstandar(60));
  print(esBusEstandar(90));
}
