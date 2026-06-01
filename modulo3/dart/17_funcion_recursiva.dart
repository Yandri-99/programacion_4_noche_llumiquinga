// Factorial — caso base y caso recursivo
int factorial(int n) {
  if (n <= 1) return 1;       // caso base
  return n * factorial(n - 1); // llamada recursiva
}

// Fibonacci — árbol de llamadas
int fibonacci(int n) {
  if (n <= 1) return n;
  return fibonacci(n - 1) + fibonacci(n - 2);
}

// Búsqueda en árbol de directorios (simulado)
int contarArchivos(Map<String, dynamic> directorio) {
  int total = 0;
  for (final entrada in directorio.entries) {
    if (entrada.value is Map) {
      // Es un subdirectorio — llamada recursiva
      total += contarArchivos(entrada.value as Map<String, dynamic>);
    } else {
      total++;  // Es un archivo
    }
  }
  return total;
}

void main() {
  print(factorial(6));    // 720
  print(fibonacci(10));   // 55

  final sistemaArchivos = {
    'src': {
      'controllers': {'user_controller.dart': true, 'auth_controller.dart': true},
      'models':      {'user.dart': true},
      'services':    {'api_service.dart': true, 'cache_service.dart': true},
    },
    'test': {'widget_test.dart': true},
    'pubspec.yaml': true,
    'README.md':    true,
  };

  print('Total de archivos: ${contarArchivos(sistemaArchivos)}');  // 9
}