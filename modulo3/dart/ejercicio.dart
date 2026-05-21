import 'dart:io';

void main() {
  int totalMinutos = 0;
  int clientes = 0;
  int minutos = 1;

  while (minutos > 0) {
    print("Minutos de Entrenamiento: ");
    minutos = int.parse(stdin.readLineSync()!);
    if (minutos > 0){
      if (minutos < 30){
        print("Entrenamiento insuficiente"); 
      }else if (minutos >= 30 && minutos<= 90){
        print("Entrenamiento adecuado");
      }else {
        print("Entrenamiento intenso");
      }
      totalMinutos = totalMinutos + minutos;
      clientes = clientes + 1;
    }
  }
  double promedio = 0;
  if (clientes > 0){
    promedio = totalMinutos / 
  }
  print("Total de minutos entrenados: $totalMinutos");
  print("Cantidad de clientes registrados: $clientes");
  print("Promedio de minutos por cliente: $promedio");
}