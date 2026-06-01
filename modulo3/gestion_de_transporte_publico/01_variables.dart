void main() {
  var conductor = 'Carlos';
  var edad      = 35;
  var tarifa    = 0.50;
  var rutaActiva = true;

  String apellido = 'Mendoza';
  int    capacidadBus = 60;
  double distancia    = 12.5;
  bool   enHorario    = false;

  final ciudad = 'Quito';
  final linea = 'Ruta Trolebús';

  const maxPasajeros = 120;
  const tarifaBase   = 0.35;

  final horaActual = DateTime.now();

  print('$conductor $apellido conduce la $linea en $ciudad');

  var contadorPasajeros = 0;
  contadorPasajeros = 15;

  final paradas = ['La Marín', 'El Recreo', 'Quitumbe'];
  paradas.add('El Labrador');

  print(paradas);

  String nombre = 'Estación Norte';

  String? paradero = null;
  paradero = 'El Ejido';

  String? estacion;

  print(estacion?.length);

  String resultado = estacion ?? 'Sin estación asignada';
  print(resultado);

  if (paradero != null) {
    print(paradero.length);
  }

  late String codigoRuta;
  codigoRuta = 'RUTA-001';
  print(codigoRuta);

  List<String> rutas = ['Trolebús', 'Metrobús', 'Ecovía'];
  var numerosRuta = [1, 2, 3, 4, 5];

  print(rutas[0]);
  print(rutas.length);
  rutas.add('Metro');
  rutas.remove('Ecovía');

  Map<String, int> frecuenciaRutas = {
    'Trolebús': 5,
    'Metrobús': 8,
    'Ecovía':   12,
  };

  print(frecuenciaRutas['Trolebús']);
  print(frecuenciaRutas['Metro']);
  frecuenciaRutas['Metro'] = 4;

  Set<String> paradasPrincipales = {'Quitumbe', 'La Marín', 'El Labrador'};
  paradasPrincipales.add('Quitumbe');
  print(paradasPrincipales.length);

  var r1 = ['Estación Norte', 'Estación Central'];
  var r2 = ['Estación Sur', 'Terminal'];
  var combinadas = [...r1, ...r2];
  print(combinadas);

  bool tieneDesvio = true;
  var paraderos = [
    'Parada 1',
    'Parada 2',
    if (tieneDesvio) 'Parada Alterna',
  ];
  print(paraderos);

  var cuadrantes = [for (var i = 1; i <= 5; i++) 'Zona $i'];
  print(cuadrantes);
}
