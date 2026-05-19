void main() {
  // Conversiones numéricas
  int    entero  = 42;
  double decimal = entero.toDouble();   // 42.0
  String texto   = entero.toString();   // "42"

  // String → número
  int    num1 = int.parse('123');       // 123
  double num2 = double.parse('3.14');   // 3.14

  // Conversión segura (no lanza excepción)
  int?    num3 = int.tryParse('abc');   // null
  double? num4 = double.tryParse('99'); // 99.0

  // Verificar tipo con is (como en Kotlin)
  Object valor = 'texto';
  if (valor is String) {
    print(valor.length);  // smart cast — ya es String
  }

  // Cast explícito con as
  Object obj = 'Hola';
  String str = obj as String;

  // Comprobar nulabilidad
  String? nullable = null;
  int longitud = nullable?.length ?? 0;
  print(longitud);  // 0

  // Números especiales
  print(double.infinity);     // Infinity
  print(double.nan);          // NaN
  print(double.maxFinite);    // 1.7976931348623157e+308
}