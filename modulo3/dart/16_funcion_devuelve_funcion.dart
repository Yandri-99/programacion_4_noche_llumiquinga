// Fábrica de multiplicadores — devuelve una función configurada
int Function(int) crearMultiplicador(int factor) {
  return (int n) => n * factor;
}

void main() {
  final doble    = crearMultiplicador(2);
  final triple   = crearMultiplicador(3);
  final decuplo  = crearMultiplicador(10);

  print(doble(5));    // 10
  print(triple(5));   // 15
  print(decuplo(5));  // 50

  // Útil para generar validadores configurables
  bool Function(double) crearValidadorPrecio(double min, double max) {
    return (precio) => precio >= min && precio <= max;
  }

  final esEconomico  = crearValidadorPrecio(0, 50);
  final esPremium    = crearValidadorPrecio(200, double.infinity);

  print(esEconomico(35.0));   // true
  print(esPremium(249.99));   // true
  print(esPremium(45.0));     // false
}