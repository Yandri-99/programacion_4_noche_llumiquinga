class Bus {
  final String id;
  final String placa;
  String       ruta;
  bool         _enServicio = false;

  Bus({
    required this.id,
    required this.placa,
    required this.ruta,
  });

  bool   get enServicio => _enServicio;
  String get estado     => _enServicio ? 'en ruta' : 'en terminal';

  set estadoServicio(bool valor) {
    _enServicio = valor;
    print('Bus $placa: ${valor ? "inicia servicio" : "finaliza servicio"}');
  }

  void iniciarRuta() {
    _enServicio = true;
    print('Bus $placa iniciando ruta $ruta');
  }

  void finalizarRuta() {
    _enServicio = false;
    print('Bus $placa finalizó ruta');
  }

  String resumen() => 'ID: $id | Placa: $placa | Ruta: $ruta | Estado: $estado';

  @override
  String toString() => 'Bus($placa, $ruta, $estado)';
}

void main() {
  final bus = Bus(
    id:    'BUS-001',
    placa: 'PCH-1234',
    ruta:  'Trolebús',
  );

  bus.iniciarRuta();
  print(bus.estado);
  print(bus.resumen());
  print(bus);

  bus.estadoServicio = false;
  print(bus.enServicio);
}
