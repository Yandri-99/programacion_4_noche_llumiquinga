import 'dart:io';

void main() {

  /*EJERCICO 1
  Un gimnasio registra la cantidad de minutos que cada cliente entrenó durante el día.
Realiza un programa en Dart que use readLineSync() y un ciclo while para ingresar los minutos de entrenamiento de cada cliente.
Reglas de negocio:
Menos de 30 minutos → “Entrenamiento insuficiente”
Entre 30 y 90 minutos → “Entrenamiento adecuado”
Más de 90 minutos → “Entrenamiento intenso”
El programa debe seguir solicitando datos mientras se ingresen valores mayores a 0.
Cuando el usuario ingrese 0, mostrar:
Total de minutos entrenados
Cantidad de clientes registrados
Promedio de minutos por cliente

  */ 

  int minutos = 1;
  int total = 0;
  int clientes = 0;

  while (minutos > 0) {

    print("Ingrese minutos:");
    minutos = int.parse(stdin.readLineSync()!);

    if (minutos > 0) {
      total = total + minutos;
      clientes = clientes + 1;
      if (minutos < 30) {
        print("Entrenamiento insuficiente");
      } else if (minutos <= 90) {
        print("Entrenamiento adecuado");
      } else {
        print("Entrenamiento intenso");
      }
    }
  }
  print("Total minutos: $total");
  print("Clientes: $clientes");
  print("Promedio: ${total / clientes}");


/*EJERCICIO 2
Una carpintería registra la cantidad de muebles fabricados por cada trabajador durante la jornada laboral.
Realiza un programa en Dart que use readLineSync() y un ciclo while para ingresar la cantidad de muebles elaborados por empleado.
Reglas de negocio:
Menos de 3 muebles → “Producción baja”
Entre 3 y 7 muebles → “Producción normal”
Más de 7 muebles → “Producción alta”
El programa debe continuar solicitando datos mientras se ingresen valores mayores a 0.
Cuando el usuario ingrese 0, mostrar:
Total de muebles fabricados
Cantidad de trabajadores registrados
Promedio de muebles por trabajador

*/
int muebles = 1;
  int total1 = 0;
  int trabajadores = 0;

  while (muebles > 0) {

    print("Ingrese cantidad de muebles:");
    muebles = int.parse(stdin.readLineSync()!);

    if (muebles > 0) {

      total1 = total1 + muebles;
      trabajadores = trabajadores + 1;

      if (muebles < 3) {
        print("Producción baja");
      } else if (muebles <= 7) {
        print("Producción normal");
      } else {
        print("Producción alta");
      }

    }
  }

  print("Total de muebles fabricados: $total1");
  print("Cantidad de trabajadores: $trabajadores");
  print("Promedio: ${total / trabajadores}");

  /*EJERCICIO 3
  Un taller de carpintería controla la cantidad de tablas cortadas por cada operario durante el día.
Realiza un programa en Dart que use readLineSync() y un ciclo while para ingresar la cantidad de tablas cortadas por trabajador.
Reglas de negocio:
Menos de 15 tablas → “Trabajo lento”
Entre 15 y 40 tablas → “Trabajo eficiente”
Más de 40 tablas → “Trabajo sobresaliente”
El programa debe seguir solicitando datos mientras se ingresen valores mayores a 0.
Cuando el usuario ingrese 0, mostrar:
Total de tablas cortadas
Cantidad de operarios registrados
Promedio de tablas por operario


*/
  int tablas = 1;
  int total2 = 0;
  int operarios = 0;

  while (tablas > 0) {

    print("Ingrese cantidad de tablas cortadas:");
    tablas = int.parse(stdin.readLineSync()!);

    if (tablas > 0) {

      total2 = total2 + tablas;
      operarios = operarios + 1;

      if (tablas < 15) {
        print("Trabajo lento");
      } else if (tablas <= 40) {
        print("Trabajo eficiente");
      } else {
        print("Trabajo sobresaliente");
      }

    }
  }

  print("Total de tablas cortadas: $total");
  print("Cantidad de operarios registrados: $operarios");
  print("Promedio: ${total2 / operarios}");

  /*Un consultorio médico registra la cantidad de pacientes atendidos y las horas trabajadas por cada doctor.
Realiza un programa en Dart que use readLineSync() y un ciclo while para ingresar:
Horas trabajadas
Cantidad de pacientes atendidos
Dentro del ciclo, calcular los pacientes atendidos por hora.
Reglas de negocio:
Menos de 3 pacientes por hora → “Atención lenta”
Entre 3 y 6 pacientes por hora → “Atención normal”
Más de 6 pacientes por hora → “Atención rápida”
El programa debe continuar mientras las horas trabajadas sean mayores a 0.
Al finalizar, mostrar:
Total de pacientes atendidos
Cantidad de doctores registrados
Promedio de pacientes por doctor
*/

  int horas = 1;
  int pacientes;
  int totalPacientes = 0;
  int doctores = 0;

  while (horas > 0) {

    print("Ingrese horas trabajadas:");
    horas = int.parse(stdin.readLineSync()!);

    if (horas > 0) {

      print("Ingrese cantidad de pacientes:");
      pacientes = int.parse(stdin.readLineSync()!);

      double porHora = pacientes / horas;

      totalPacientes = totalPacientes + pacientes;
      doctores = doctores + 1;

      if (porHora < 3) {
        print("Atención lenta");
      } else if (porHora <= 6) {
        print("Atención normal");
      } else {
        print("Atención rápida");
      }

    }
  }

  print("Total de pacientes atendidos: $totalPacientes");
  print("Cantidad de doctores registrados: $doctores");
  print("Promedio de pacientes por doctor: ${totalPacientes / doctores}");

  /*EJERCICIO 5
  Un aeropuerto controla la operación diaria de sus agentes de embarque, registrando vuelos, pasajeros y retrasos.
Realiza un programa en Dart que use readLineSync() y un ciclo while para ingresar por cada agente:
Cantidad de vuelos atendidos
Cantidad de pasajeros procesados
Minutos totales de retraso
Dentro del ciclo, calcular:
Pasajeros por vuelo
Índice de retraso por vuelo:
[
\text{Retraso por vuelo} = \frac{\text{Minutos de retraso}}{\text{Vuelos}}
]
Reglas de negocio:
Menos de 50 pasajeros por vuelo → “Baja eficiencia”
Entre 50 y 120 pasajeros por vuelo → “Eficiencia normal”
Más de 120 pasajeros por vuelo → “Alta eficiencia”
El programa debe continuar mientras la cantidad de vuelos sea mayor a 0.
Al finalizar, mostrar:
Total de pasajeros procesados
Total de minutos de retraso acumulados
Cantidad de agentes registrados
Promedio de pasajeros por agente
Promedio general de retraso por vuelo
*/

  int vuelos = 1;
  int pasajeros;
  int retraso;

  int totalPasajeros = 0;
  int totalRetraso = 0;
  int agentes = 0;
  double totalRetrasoVuelo = 0;

  while (vuelos > 0) {

    print("Ingrese cantidad de vuelos:");
    vuelos = int.parse(stdin.readLineSync()!);

    if (vuelos > 0) {

      print("Ingrese cantidad de pasajeros:");
      pasajeros = int.parse(stdin.readLineSync()!);

      print("Ingrese minutos de retraso:");
      retraso = int.parse(stdin.readLineSync()!);

      double pasajerosVuelo = pasajeros / vuelos;
      double retrasoVuelo = retraso / vuelos;

      totalPasajeros = totalPasajeros + pasajeros;
      totalRetraso = totalRetraso + retraso;
      totalRetrasoVuelo = totalRetrasoVuelo + retrasoVuelo;
      agentes = agentes + 1;

      if (pasajerosVuelo < 50) {
        print("Baja eficiencia");
      } else if (pasajerosVuelo <= 120) {
        print("Eficiencia normal");
      } else {
        print("Alta eficiencia");
      }

    }
  }

  print("Total de pasajeros procesados: $totalPasajeros");
  print("Total de minutos de retraso: $totalRetraso");
  print("Cantidad de agentes registrados: $agentes");
  print("Promedio de pasajeros por agente: ${totalPasajeros / agentes}");
  print("Promedio general de retraso por vuelo: ${totalRetrasoVuelo / agentes}");

  /*EJERCICIO 6
  Un almacén registra las ventas diarias de productos durante una semana para cada vendedor.
Realiza un programa en Dart que use readLineSync() y un ciclo for para ingresar las ventas de 7 días por cada vendedor.
Dentro del ciclo, calcular:
Total de ventas por vendedor
Promedio diario de ventas
Reglas de negocio:
Menos de 10 ventas diarias → “Bajo rendimiento”
Entre 10 y 25 ventas diarias → “Rendimiento normal”
Más de 25 ventas diarias → “Alto rendimiento”
Al finalizar cada vendedor, mostrar:
Total de ventas
Promedio de ventas
Clasificación de rendimiento
*/

  int totalVentas = 0;
  double promedioVentas = 0;

  for (int dia = 1; dia <= 7; dia++) {

    print("Ingrese ventas del día $dia:");
    int ventas = int.parse(stdin.readLineSync()!);

    totalVentas = totalVentas + ventas;
  }

  promedioVentas = totalVentas / 7;

  print("Total de ventas: $totalVentas");
  print("Promedio de ventas: $promedioVentas");

  if (promedioVentas < 10) {
    print("Bajo rendimiento");
  } else if (promedioVentas <= 25) {
    print("Rendimiento normal");
  } else {
    print("Alto rendimiento");
  }

  /* EJERCICIO 7
  Una empresa de transporte registra los kilómetros recorridos por sus conductores durante un turno de 5 viajes.
Realiza un programa en Dart que use readLineSync() y un ciclo for para ingresar los kilómetros de cada viaje.
Dentro del ciclo, calcular:
Total de kilómetros recorridos
Consumo estimado de combustible (1 litro por cada 12 km):

Reglas de negocio:
Menos de 50 km por viaje → “Ruta corta”
Entre 50 y 150 km por viaje → “Ruta media”
Más de 150 km por viaje → “Ruta larga”
Al finalizar, mostrar:
Total de kilómetros recorridos
Total de combustible estimado
Promedio de kilómetros por viaje
 
*/

  int totalKm = 0;

  for (int viaje = 1; viaje <= 5; viaje++) {

    print("Ingrese kilómetros del viaje $viaje:");
    int km = int.parse(stdin.readLineSync()!);

    totalKm = totalKm + km;

    if (km < 50) {
      print("Ruta corta");
    } else if (km <= 150) {
      print("Ruta media");
    } else {
      print("Ruta larga");
    }

  }

  double combustible = totalKm / 12;
  double promedio = totalKm / 5;

  print("Total de kilómetros: $totalKm");
  print("Total de combustible estimado: $combustible litros");
  print("Promedio de kilómetros por viaje: $promedio");

}