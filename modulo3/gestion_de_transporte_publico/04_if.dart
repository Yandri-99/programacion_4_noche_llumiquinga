void main() {
  int pasajerosAbordo = 55;

  if (pasajerosAbordo > 60) {
    print('Bus lleno — excede capacidad');
  } else if (pasajerosAbordo > 40) {
    print('Capacidad media');
  } else {
    print('Bus con espacio disponible');
  }

  String estado = pasajerosAbordo > 60 ? 'Excede capacidad' : 'Dentro del límite';
  print(estado);

  int? retraso;
  String display = retraso != null ? '${retraso} min de retraso' : 'A tiempo';

  String display2 = retraso?.toString() ?? 'A tiempo';
  print(display2);
}
