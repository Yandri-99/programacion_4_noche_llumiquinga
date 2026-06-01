void main() {
  int    pasajeros  = 42;
  double capacidad  = pasajeros.toDouble();
  String texto      = pasajeros.toString();

  int    num1 = int.parse('123');
  double num2 = double.parse('3.14');

  int?    num3 = int.tryParse('abc');
  double? num4 = double.tryParse('99');

  Object valor = 'Trolebús';
  if (valor is String) {
    print(valor.length);
  }

  Object obj = 'Quitumbe';
  String str = obj as String;

  String? recorrido = null;
  int km = recorrido?.length ?? 0;
  print(km);

  print(double.infinity);
  print(double.nan);
  print(double.maxFinite);
}
