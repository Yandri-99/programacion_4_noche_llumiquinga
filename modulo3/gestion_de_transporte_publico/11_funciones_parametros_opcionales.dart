String construirUrlRuta(String host, String ruta, [int? puerto]) {
  if (puerto != null) {
    return 'https://$host:$puerto$ruta';
  }
  return 'https://$host$ruta';
}

String construirUrlRutaV2(String host, String ruta, [int puerto = 443]) {
  return 'https://$host:$puerto$ruta';
}

void main() {
  print(construirUrlRuta('api.transporte.com', '/rutas'));
  print(construirUrlRuta('api.transporte.com', '/buses', 8080));
  print(construirUrlRutaV2('api.transporte.com', '/paradas'));

  void configurarGPS({
    required String host,
    required int    puerto,
    bool   ssl        = true,
    int    timeoutSeg = 30,
  }) {
    final protocolo = ssl ? 'https' : 'http';
    print('Conectando a $protocolo://$host:$puerto (timeout: ${timeoutSeg}s)');
  }

  void main() {
    configurarGPS(
      host:       'gps.transporte.com',
      puerto:     8080,
      ssl:        false,
      timeoutSeg: 60,
    );

    configurarGPS(
      host:   'api.transporte.com',
      puerto: 443,
    );
  }
}
