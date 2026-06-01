import 'dart:io';

void main() {
  print('Ingrese el nombre del conductor');
  String? nombre = stdin.readLineSync();
  print('Conductor: $nombre');

  print('Ingrese el número de ruta:');
  int? ruta = int.parse(stdin.readLineSync()!);
  print('Ruta asignada: $ruta');

  print('Ingrese la tarifa:');
  double? tarifa = double.parse(stdin.readLineSync()!);
  print('Tarifa: \$$tarifa');

  print('Ingrese un número para la tabla de frecuencias:');
  int? numero = int.parse(stdin.readLineSync()!);
  print('Tabla de frecuencias de la ruta $numero:');
  for (int i = 1; i < 11; i++) {
    print('$i x $numero = ${i * numero} min');
  }

  print('Ingrese cantidad de pasajeros: ');
  int? pasajeros = int.parse(stdin.readLineSync()!);
  if (pasajeros % 2 == 0) {
    print('Cantidad par de pasajeros');
  } else {
    print('Cantidad impar de pasajeros');
  }

  print('Ingrese código de bus (0 para salir): ');
  int codigo = 1;
  while (codigo != 0) {
    print('Ingrese código de bus:');
    codigo = int.parse(stdin.readLineSync()!);
  }
  print('Finalizó el ingreso de buses');
}
