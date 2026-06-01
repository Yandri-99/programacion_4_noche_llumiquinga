class Ruta {
  final String nombre;
  final String origen;
  final String destino;
  final double tarifa;
  final bool   activa;

  Ruta({
    required this.nombre,
    required this.origen,
    required this.destino,
    required this.tarifa,
    this.activa = true,
  });

  Ruta.local()
      : nombre = 'Ruta Local',
        origen = 'Terminal Norte',
        destino = 'Mercado Central',
        tarifa = 0.25,
        activa = true;

  Ruta.expresa({required this.nombre, required this.origen, required this.destino})
      : tarifa = 0.50,
        activa = true;

  factory Ruta.desdeJson(String json) {
    final partes = json.split(',');
    return Ruta(
      nombre:  partes[0],
      origen:  partes[1],
      destino: partes[2],
      tarifa:  double.parse(partes[3]),
    );
  }

  @override
  String toString() =>
      '$nombre: $origen → $destino (${activa ? "Activa" : "Inactiva"})';
}

void main() {
  final r1 = Ruta(nombre: 'Trolebús', origen: 'Quitumbe', destino: 'El Labrador', tarifa: 0.35);
  final r2 = Ruta.local();
  final r3 = Ruta.expresa(nombre: 'Metrobús', origen: 'Terminal Sur', destino: 'Ofelia');
  final r4 = Ruta.desdeJson('Ecovía,Río Coca,La Marín,0.30');

  print(r1);
  print(r2);
  print(r3);
  print(r4);
}
