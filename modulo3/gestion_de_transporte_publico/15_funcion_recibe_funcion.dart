List<int> filtrarBuses(List<int> lista, bool Function(int) criterio) {
  return lista.where(criterio).toList();
}

bool tieneCapacidadSuficiente(int n) => n >= 50;
bool esArticulado(int n) => n > 80;

void main() {
  final capacidades = [30, 60, 90, 45, 100, 70, 120];

  print(filtrarBuses(capacidades, tieneCapacidadSuficiente));
  print(filtrarBuses(capacidades, esArticulado));

  print(filtrarBuses(capacidades, (n) => n % 30 == 0));
}
