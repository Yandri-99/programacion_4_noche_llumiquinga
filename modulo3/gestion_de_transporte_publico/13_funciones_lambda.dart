void main() {
  final calcularDistancia = (int km) => km * 2;
  print(calcularDistancia(7));

  final calcularTarifaConDescuento = (double tarifa, double pct) {
    final descuento = tarifa * (pct / 100);
    return tarifa - descuento;
  };
  print(calcularTarifaConDescuento(0.50, 10.0));

  final capacidades = [60, 45, 80, 30, 100, 55, 70];
  capacidades.sort((a, b) => b.compareTo(a));
  print(capacidades);
}
