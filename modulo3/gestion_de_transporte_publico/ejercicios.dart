import 'dart:io';

void main() {

  /*EJERCICIO 1
  Una empresa de transporte registra la cantidad de pasajeros que cada bus transportó durante el día.
  Realiza un programa en Dart que use readLineSync() y un ciclo while para ingresar la cantidad de pasajeros de cada bus.
  Reglas de negocio:
  Menos de 30 pasajeros → "Baja ocupación"
  Entre 30 y 90 pasajeros → "Ocupación normal"
  Más de 90 pasajeros → "Ocupación alta"
  El programa debe seguir solicitando datos mientras se ingresen valores mayores a 0.
  Cuando el usuario ingrese 0, mostrar:
  Total de pasajeros transportados
  Cantidad de buses registrados
  Promedio de pasajeros por bus
  */

  int pasajeros = 1;
  int total = 0;
  int buses = 0;

  while (pasajeros > 0) {

    print("Ingrese cantidad de pasajeros:");
    pasajeros = int.parse(stdin.readLineSync()!);

    if (pasajeros > 0) {
      total = total + pasajeros;
      buses = buses + 1;
      if (pasajeros < 30) {
        print("Baja ocupación");
      } else if (pasajeros <= 90) {
        print("Ocupación normal");
      } else {
        print("Ocupación alta");
      }
    }
  }
  print("Total pasajeros: $total");
  print("Buses: $buses");
  print("Promedio: ${total / buses}");


  /*EJERCICIO 2
  Un terminal terrestre registra la cantidad de viajes realizados por cada transportista durante la jornada.
  Realiza un programa en Dart que use readLineSync() y un ciclo while para ingresar la cantidad de viajes por transportista.
  Reglas de negocio:
  Menos de 3 viajes → "Producción baja"
  Entre 3 y 7 viajes → "Producción normal"
  Más de 7 viajes → "Producción alta"
  El programa debe continuar solicitando datos mientras se ingresen valores mayores a 0.
  Cuando el usuario ingrese 0, mostrar:
  Total de viajes realizados
  Cantidad de transportistas registrados
  Promedio de viajes por transportista
  */
  int viajes = 1;
  int total1 = 0;
  int transportistas = 0;

  while (viajes > 0) {

    print("Ingrese cantidad de viajes:");
    viajes = int.parse(stdin.readLineSync()!);

    if (viajes > 0) {

      total1 = total1 + viajes;
      transportistas = transportistas + 1;

      if (viajes < 3) {
        print("Producción baja");
      } else if (viajes <= 7) {
        print("Producción normal");
      } else {
        print("Producción alta");
      }

    }
  }

  print("Total de viajes realizados: $total1");
  print("Cantidad de transportistas: $transportistas");
  print("Promedio: ${total / transportistas}");

  /*EJERCICIO 3
  Un centro de control de tráfico registra la cantidad de buses despachados por cada operador durante el turno.
  Realiza un programa en Dart que use readLineSync() y un ciclo while para ingresar la cantidad de buses despachados por operador.
  Reglas de negocio:
  Menos de 15 buses → "Despacho lento"
  Entre 15 y 40 buses → "Despacho eficiente"
  Más de 40 buses → "Despacho sobresaliente"
  El programa debe seguir solicitando datos mientras se ingresen valores mayores a 0.
  Cuando el usuario ingrese 0, mostrar:
  Total de buses despachados
  Cantidad de operadores registrados
  Promedio de buses por operador
  */
  int busesDespachados = 1;
  int total2 = 0;
  int operadores = 0;

  while (busesDespachados > 0) {

    print("Ingrese cantidad de buses despachados:");
    busesDespachados = int.parse(stdin.readLineSync()!);

    if (busesDespachados > 0) {

      total2 = total2 + busesDespachados;
      operadores = operadores + 1;

      if (busesDespachados < 15) {
        print("Despacho lento");
      } else if (busesDespachados <= 40) {
        print("Despacho eficiente");
      } else {
        print("Despacho sobresaliente");
      }

    }
  }

  print("Total de buses despachados: $total");
  print("Cantidad de operadores registrados: $operadores");
  print("Promedio: ${total2 / operadores}");

  /*EJERCICIO 4
  Un sistema de transporte registra la cantidad de rutas atendidas y las horas operadas por cada conductor.
  Realiza un programa en Dart que use readLineSync() y un ciclo while para ingresar:
  Horas trabajadas
  Cantidad de rutas atendidas
  Dentro del ciclo, calcular las rutas por hora.
  Reglas de negocio:
  Menos de 3 rutas por hora → "Rendimiento lento"
  Entre 3 y 6 rutas por hora → "Rendimiento normal"
  Más de 6 rutas por hora → "Rendimiento rápido"
  El programa debe continuar mientras las horas trabajadas sean mayores a 0.
  Al finalizar, mostrar:
  Total de rutas atendidas
  Cantidad de conductores registrados
  Promedio de rutas por conductor
  */
  int horas = 1;
  int rutasAtendidas;
  int totalRutas = 0;
  int conductores = 0;

  while (horas > 0) {

    print("Ingrese horas trabajadas:");
    horas = int.parse(stdin.readLineSync()!);

    if (horas > 0) {

      print("Ingrese cantidad de rutas atendidas:");
      rutasAtendidas = int.parse(stdin.readLineSync()!);

      double porHora = rutasAtendidas / horas;

      totalRutas = totalRutas + rutasAtendidas;
      conductores = conductores + 1;

      if (porHora < 3) {
        print("Rendimiento lento");
      } else if (porHora <= 6) {
        print("Rendimiento normal");
      } else {
        print("Rendimiento rápido");
      }

    }
  }

  print("Total de rutas atendidas: $totalRutas");
  print("Cantidad de conductores registrados: $conductores");
  print("Promedio de rutas por conductor: ${totalRutas / conductores}");

  /*EJERCICIO 5
  Una estación de transporte controla la operación diaria de sus inspectores, registrando buses, pasajeros y demoras.
  Realiza un programa en Dart que use readLineSync() y un ciclo while para ingresar por cada inspector:
  Cantidad de buses revisados
  Cantidad de pasajeros encuestados
  Minutos totales de demora
  Dentro del ciclo, calcular:
  Pasajeros por bus
  Índice de demora por bus
  Reglas de negocio:
  Menos de 50 pasajeros por bus → "Baja eficiencia"
  Entre 50 y 120 pasajeros por bus → "Eficiencia normal"
  Más de 120 pasajeros por bus → "Alta eficiencia"
  El programa debe continuar mientras la cantidad de buses sea mayor a 0.
  Al finalizar, mostrar:
  Total de pasajeros encuestados
  Total de minutos de demora acumulados
  Cantidad de inspectores registrados
  Promedio de pasajeros por inspector
  Promedio general de demora por bus
  */
  int busesRevisados = 1;
  int pasajerosEncuestados;
  int demora;

  int totalPasajerosEncuestados = 0;
  int totalDemora = 0;
  int inspectores = 0;
  double totalDemoraBus = 0;

  while (busesRevisados > 0) {

    print("Ingrese cantidad de buses revisados:");
    busesRevisados = int.parse(stdin.readLineSync()!);

    if (busesRevisados > 0) {

      print("Ingrese cantidad de pasajeros encuestados:");
      pasajerosEncuestados = int.parse(stdin.readLineSync()!);

      print("Ingrese minutos de demora:");
      demora = int.parse(stdin.readLineSync()!);

      double pasajerosBus = pasajerosEncuestados / busesRevisados;
      double demoraBus = demora / busesRevisados;

      totalPasajerosEncuestados = totalPasajerosEncuestados + pasajerosEncuestados;
      totalDemora = totalDemora + demora;
      totalDemoraBus = totalDemoraBus + demoraBus;
      inspectores = inspectores + 1;

      if (pasajerosBus < 50) {
        print("Baja eficiencia");
      } else if (pasajerosBus <= 120) {
        print("Eficiencia normal");
      } else {
        print("Alta eficiencia");
      }

    }
  }

  print("Total de pasajeros encuestados: $totalPasajerosEncuestados");
  print("Total de minutos de demora: $totalDemora");
  print("Cantidad de inspectores registrados: $inspectores");
  print("Promedio de pasajeros por inspector: ${totalPasajerosEncuestados / inspectores}");
  print("Promedio general de demora por bus: ${totalDemoraBus / inspectores}");

  /*EJERCICIO 6
  Una cooperativa de transporte registra los pasajeros diarios transportados durante una semana por cada ruta.
  Realiza un programa en Dart que use readLineSync() y un ciclo for para ingresar los pasajeros de 7 días por cada ruta.
  Dentro del ciclo, calcular:
  Total de pasajeros por ruta
  Promedio diario de pasajeros
  Reglas de negocio:
  Menos de 100 pasajeros diarios → "Baja demanda"
  Entre 100 y 500 pasajeros diarios → "Demanda normal"
  Más de 500 pasajeros diarios → "Alta demanda"
  Al finalizar cada ruta, mostrar:
  Total de pasajeros
  Promedio de pasajeros
  Clasificación de demanda
  */
  int totalPasajerosRuta = 0;
  double promedioPasajeros = 0;

  for (int dia = 1; dia <= 7; dia++) {

    print("Ingrese pasajeros del día $dia:");
    int pax = int.parse(stdin.readLineSync()!);

    totalPasajerosRuta = totalPasajerosRuta + pax;
  }

  promedioPasajeros = totalPasajerosRuta / 7;

  print("Total de pasajeros: $totalPasajerosRuta");
  print("Promedio de pasajeros: $promedioPasajeros");

  if (promedioPasajeros < 100) {
    print("Baja demanda");
  } else if (promedioPasajeros <= 500) {
    print("Demanda normal");
  } else {
    print("Alta demanda");
  }

  /* EJERCICIO 7
  Una empresa de transporte registra los kilómetros recorridos por sus conductores durante un turno de 5 viajes.
  Realiza un programa en Dart que use readLineSync() y un ciclo for para ingresar los kilómetros de cada viaje.
  Dentro del ciclo, calcular:
  Total de kilómetros recorridos
  Consumo estimado de combustible (1 litro por cada 12 km)

  Reglas de negocio:
  Menos de 50 km por viaje → "Ruta corta"
  Entre 50 y 150 km por viaje → "Ruta media"
  Más de 150 km por viaje → "Ruta larga"
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
