void main() {
  final tarifas = [0.35, 0.50, 0.25, 0.80];

  final tarifasConIVA = tarifas.map((t) => t * 1.15);
  print(tarifasConIVA.toList());

  final paradas = ['Quitumbe', 'El Recreo', 'La Marín'];
  final urls = paradas.map((e) => 'https://api.transporte.com/parada/$e');
  print(urls.toList());

  final velocidades = [25.0, 40.0, 55.0, 30.0, 60.0, 20.0];

  final excesos = velocidades.where((v) => v > 50);
  print(excesos.toList());

  final normales = velocidades.where((v) => v >= 20.0 && v <= 50.0);
  print(normales.toList());

  final distancias = [12.0, 25.0, 8.0, 30.0, 15.0];

  final total = distancias.reduce((acum, d) => acum + d);
  print('Total km: ${total.toStringAsFixed(2)}');

  final totalFold = distancias.fold(0.0, (acum, d) => acum + d);
  print('Total km (fold): ${totalFold.toStringAsFixed(2)}');

  final maximo = distancias.reduce((a, b) => a > b ? a : b);
  print('Mayor distancia: $maximo km');
}
