import 'dart:io';

void main() {
  print('Ingrese su nombre');
  String? nombre = stdin.readLineSync();
  print('Hola, $nombre');

  print('Ingrese un número entero:');
  int? numero = int.parse(stdin.readLineSync()!);
  print('El número es: $numero');

  print('Ingrese un decimal:');
  double? decimal = double.parse(stdin.readLineSync()!);
  print('El decimal es: $decimal');

  //Multiplicacion
  print('Ingrese un número para multiplicar:');
  int? numero3 = int.parse(stdin.readLineSync()!);
  print('El número es: $numero3');
  for (int i = 1; i < 11; i++) {
    print('La multiplicación es:$i x $numero3 = ${i * numero3}');
  }

  //Positivo Negativo
  print('Ingrese un numero: ');
  int? numero4 = int.parse(stdin.readLineSync()!);
  print('El número es: $numero4');
  if (numero4 % 2 == 0) {
    print('El número es positivo');
  } else {
    print('El número es negativo');
  }

  //while
  print('Ingrese el numero: ');
  int numero5 = 1; 
  while (numero5 != 0) {
    print('Ingrese un número:');
    numero5 = int.parse(stdin.readLineSync()!);
  }
  print('La suma total de los números ingresados es: $numero5');


}

