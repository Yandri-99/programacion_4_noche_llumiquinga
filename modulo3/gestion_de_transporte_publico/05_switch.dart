void main() {
  String estadoRuta = 'activa';

  switch (estadoRuta) {
    case 'activa':
      print('Ruta en operación');
    case 'suspendida':
      print('Ruta suspendida');
    case 'mantenimiento':
      print('Ruta en mantenimiento');
    case 'desvio':
      print('Ruta con desvío');
    case 'cancelada':
      print('Ruta cancelada');
    default:
      print('Estado desconocido');
  }

  estadoRuta = 'activa';

  String descripcion = switch (estadoRuta) {
    'activa'       => 'Ruta funcionando con normalidad',
    'suspendida'   => 'Ruta fuera de servicio',
    'mantenimiento' => 'Ruta en mantenimiento programado',
    'desvio'        => 'Ruta con desvío temporal',
    'cancelada'     => 'Ruta cancelada por evento',
    _               => 'Estado de ruta desconocido',
  };

  print(descripcion);

  int codigoNumerico = 404;

  String categoria = switch (codigoNumerico) {
    200 || 201 || 204       => 'Operación normal (2xx)',
    301 || 302 || 307       => 'Redirección de ruta (3xx)',
    400 || 401 || 403 || 404 => 'Error de operación (4xx)',
    500 || 502 || 503       => 'Error del sistema (5xx)',
    _                       => 'Desconocido',
  };

  print(categoria);

  double pasajerosPorBus = 39.2;

  String alerta = switch (pasajerosPorBus) {
    double p when p >= 60.0 => 'CRÍTICO — bus completamente lleno',
    double p when p >= 50.0 => 'OCUPACIÓN ALTA — considere refuerzo',
    double p when p >= 35.0 => 'OCUPACIÓN MEDIA — aceptable',
    double p when p >= 15.0 => 'OCUPACIÓN BAJA — normal',
    _                       => 'BUS VACÍO — optimizar ruta',
  };

  print(alerta);

  Object respuestaApi = {'id': 1, 'nombre': 'Trolebús', 'capacidad': 120};

  String resultado = switch (respuestaApi) {
    Map<String, dynamic> m when m.containsKey('error') =>
        'Error: ${m['error']}',
    Map<String, dynamic> m =>
        'Bus: ${m['nombre']} — capacidad ${m['capacidad']} pasajeros',
    List<dynamic> lista =>
        '${lista.length} rutas en la lista',
    String texto =>
        'Texto recibido: $texto',
    _ =>
        'Respuesta desconocida',
  };

  print(resultado);
}
