void main() {
  // for con índice — cuando necesitas el número de iteración
  for (int i = 0; i < 5; i++) {
    print('Iteración $i');
  }

  // for con paso distinto
  for (int i = 0; i <= 100; i += 25) {
    print('Progreso: $i%');
  }

  // for decreciente
  for (int i = 5; i >= 1; i--) {
    print('Cuenta regresiva: $i');
  }

   final protocolos = ['HTTP', 'HTTPS', 'FTP', 'SSH', 'SMTP'];

  // for-in — la forma idiomática para recorrer listas
  for (final protocolo in protocolos) {
    print(protocolo);
  }

  // forEach con lambda — alternativa funcional
  protocolos.forEach((p) => print(p.toLowerCase()));

  // for-in sobre un Map
  final puertos = {'HTTP': 80, 'HTTPS': 443, 'SSH': 22, 'FTP': 21};
  for (final entrada in puertos.entries) {
    print('${entrada.key} → puerto ${entrada.value}');
  }

  // for-in sobre caracteres de un String
  /*
  for (final caracter in 'Dart') {
    print(caracter);
  }
  */
}