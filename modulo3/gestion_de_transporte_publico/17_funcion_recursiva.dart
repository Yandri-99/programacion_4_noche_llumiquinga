int factorial(int n) {
  if (n <= 1) return 1;
  return n * factorial(n - 1);
}

int fibonacci(int n) {
  if (n <= 1) return n;
  return fibonacci(n - 1) + fibonacci(n - 2);
}

int contarParadas(Map<String, dynamic> ruta) {
  int total = 0;
  for (final entrada in ruta.entries) {
    if (entrada.value is Map) {
      total += contarParadas(entrada.value as Map<String, dynamic>);
    } else {
      total++;
    }
  }
  return total;
}

void main() {
  print(factorial(6));
  print(fibonacci(10));

  final redTransporte = {
    'Terminal Norte': {
      'Andén A': {'Trolebús': true, 'Metrobús': true},
      'Andén B': {'Ecovía': true},
    },
    'Terminal Sur': {
      'Andén C': {'Trolebús': true, 'Alimentador': true, 'Interparroquial': true},
    },
    'Terminal Central': {'Metro': true},
    'Estación Ofelia': true,
  };

  print('Total de paradas en la red: ${contarParadas(redTransporte)}');
}
