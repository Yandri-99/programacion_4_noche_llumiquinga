int calcularTarifaBasica(int km) => km * 2;
int calcularTarifaPremium(int km) => km * 5;

void main() {
  int Function(int) calculoTarifa;

  calculoTarifa = calcularTarifaBasica;
  print(calculoTarifa(10));

  calculoTarifa = calcularTarifaPremium;
  print(calculoTarifa(10));

  final calculos = <int Function(int)>[calcularTarifaBasica, calcularTarifaPremium];
  for (final fn in calculos) {
    print(fn(20));
  }
}
