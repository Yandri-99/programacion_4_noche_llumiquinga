void anunciarRuta() {
  print('Bienvenido al transporte público');
}

void saludarConductor(String nombre) {
  print('Conductor: $nombre');
}

int sumarPasajeros(int a, int b) {
  return a + b;
}

int obtenerCapacidad() {
  return 60;
}

int calcularPasajeros(int a, int b) => a * b;

void saludarOpcional(String nombre, [String apellido = 'Sin Apellido']) {
  print('Hola $nombre $apellido');
}

void registroConductor({
  required String nombre,
  required int edad,
}) {
  print("Conductor $nombre registrado");
}

String formatearTarifa(double tarifa) => '\$${tarifa.toStringAsFixed(2)}';

void main() {
  anunciarRuta();
  saludarConductor('Pedro Pérez');
  int capacidad = obtenerCapacidad();
  print(capacidad);
  print('la capacidad es: ${obtenerCapacidad()}');
  print('total pasajeros: ${sumarPasajeros(25, 30)}');
  print('total asientos: ${calcularPasajeros(2, 30)}');
  saludarOpcional('Carlos', 'Mendoza');
  saludarOpcional('Carlos');
  registroConductor(
    nombre: 'Ana',
    edad: 30,
  );
  print(formatearTarifa(0.50));
}
