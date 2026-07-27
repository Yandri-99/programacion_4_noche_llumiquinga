void main() {
  final precios = [29.99, 49.50, 15.00, 99.99];

  // map devuelve un Iterable con cada elemento transformado
  final preciosConIva = precios.map((p) => p * 1.15);
  print(preciosConIva.toList());
  // [34.4885, 56.925, 17.25, 114.9885]

  // map sobre Strings
  final endpoints = ['/usuarios', '/productos', '/pedidos'];
  final urls = endpoints.map((e) => 'https://api.ejemplo.com$e');
  print(urls.toList());
  // [https://api.ejemplo.com/usuarios, ...]

  void main() {
  final temperaturas = [36.1, 37.8, 39.2, 36.5, 38.7, 35.9];

  final conFiebre = temperaturas.where((t) => t > 37.5);
  print(conFiebre.toList());  // [37.8, 39.2, 38.7]

  final normales = temperaturas.where((t) => t >= 36.0 && t <= 37.5);
  print(normales.toList());   // [36.1, 36.5]
}

void main() {
  final ventas = [1500.0, 2300.0, 980.0, 3100.0, 750.0];

  // reduce — combina todos los elementos en uno
  final total = ventas.reduce((acum, venta) => acum + venta);
  print('Total: \$${total.toStringAsFixed(2)}');  // Total: $8630.00

  // fold — como reduce pero con valor inicial (más seguro con listas vacías)
  final totalFold = ventas.fold(0.0, (acum, venta) => acum + venta);
  print('Total (fold): \$${totalFold.toStringAsFixed(2)}');

  // Encontrar el máximo
  final maximo = ventas.reduce((a, b) => a > b ? a : b);
  print('Mayor venta: \$$maximo');  // Mayor venta: $3100.0
}
}