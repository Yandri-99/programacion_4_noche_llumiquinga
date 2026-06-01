void main() {
  int pasajeros = 0;
  int capacidad = 60;

  while (pasajeros < capacidad) {
    final suben = capacidad - pasajeros > 10 ? 10 : capacidad - pasajeros;
    pasajeros += suben;
    print('Subieron $suben pasajeros (total: $pasajeros, disponibles: ${capacidad - pasajeros})');
  }

  int intentos = 0;
  bool conexionExitosa = false;

  do {
    intentos++;
    print('Intento de conexión al GPS #$intentos...');
    if (intentos == 3) conexionExitosa = true;
  } while (!conexionExitosa && intentos < 5);

  print(conexionExitosa
      ? 'GPS conectado tras $intentos intentos'
      : 'No se pudo conectar el GPS');
}
