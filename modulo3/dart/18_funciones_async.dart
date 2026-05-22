import 'dart:io';

// async → la función retorna Future<String>
Future<String> obtenerIpPublica() async {
  // await suspende la ejecución hasta que el Future se resuelva
  await Future.delayed(Duration(milliseconds: 200));  // simula latencia
  return '203.0.113.42';
}

// void main también puede ser async
void main() async {
  print('Consultando IP...');
  final ip = await obtenerIpPublica();
  print('IP pública: $ip');
  print('Consulta completada');
}