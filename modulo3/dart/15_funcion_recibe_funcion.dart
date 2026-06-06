// Tipo explícito del parámetro función: bool Function(int)
List<int> filtrar(List<int> lista, bool Function(int) criterio) {
  return lista.where(criterio).toList();
}

bool esPar(int n)    => n % 2 == 0;
bool esGrande(int n) => n > 100;

void main() {
  final datos = [12, 7, 200, 4, 150, 33, 88, 301];

  print(filtrar(datos, esPar));      // [12, 200, 4, 150, 88]
  print(filtrar(datos, esGrande));   // [200, 150, 301]

  // Lambda en línea como argumento
  print(filtrar(datos, (n) => n % 3 == 0));  // [12, 33, 300] → [12, 33]

  
}