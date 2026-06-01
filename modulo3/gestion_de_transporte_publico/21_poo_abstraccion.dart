abstract class Vehiculo {
  String get nombre;
  double calcularCapacidad();
  double calcularConsumo();

  void describir() {
    print('$nombre — capacidad: ${calcularCapacidad().toStringAsFixed(0)} pax, '
          'consumo: ${calcularConsumo().toStringAsFixed(1)} km/l');
  }
}

class Buses extends Vehiculo {
  final double capacidadPax;
  final double rendimiento;
  Buses(this.capacidadPax, this.rendimiento);

  @override String get nombre => 'Bus ($capacidadPax pax)';
  @override double calcularCapacidad() => capacidadPax;
  @override double calcularConsumo() => rendimiento;
}

class Metro extends Vehiculo {
  final int vagones;
  Metro(this.vagones);

  @override String get nombre => 'Metro ($vagones vagones)';
  @override double calcularCapacidad() => vagones * 200;
  @override double calcularConsumo() => 5.0;
}

void main() {
  final vehiculos = <Vehiculo>[Buses(60, 3.5), Metro(6)];
  for (final v in vehiculos) {
    v.describir();
  }
}
