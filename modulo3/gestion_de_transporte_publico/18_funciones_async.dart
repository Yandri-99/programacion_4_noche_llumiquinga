import 'dart:io';

Future<String> obtenerUbicacionBus() async {
  await Future.delayed(Duration(milliseconds: 200));
  return '-0.2295, -78.5243';
}

void main() async {
  print('Consultando ubicación del bus...');
  final ubicacion = await obtenerUbicacionBus();
  print('Ubicación GPS: $ubicacion');
  print('Consulta completada');
}
