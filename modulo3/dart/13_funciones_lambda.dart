void main() {
  // Lambda asignada a una variable
  final cuadrado = (int n) => n * n;
  print(cuadrado(7));  // 49

  // Lambda de cuerpo completo
  final calcularDescuento = (double precio, double pct) {
    final descuento = precio * (pct / 100);
    return precio - descuento;
  };
  print(calcularDescuento(100.0, 15.0));  // 85.0

  // Lambda en línea — pasada directamente como argumento
  final numeros = [3, 1, 4, 1, 5, 9, 2, 6];
  numeros.sort((a, b) => b.compareTo(a));  // orden descendente
  print(numeros);  // [9, 6, 5, 4, 3, 2, 1, 1]
}