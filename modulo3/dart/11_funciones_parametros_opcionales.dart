// El tercer parámetro es opcional — puede omitirse al llamar
String construirUrl(String host, String ruta, [int? puerto]) {
  if (puerto != null) {
    return 'https://$host:$puerto$ruta';
  }
  return 'https://$host$ruta';
}

// Con valor por defecto — evita el chequeo de null
String construirUrlV2(String host, String ruta, [int puerto = 443]) {
  return 'https://$host:$puerto$ruta';
}

void main() {
  print(construirUrl('api.ejemplo.com', '/usuarios'));          // https://api.ejemplo.com/usuarios
  print(construirUrl('api.ejemplo.com', '/usuarios', 8080));   // https://api.ejemplo.com:8080/usuarios
  print(construirUrlV2('api.ejemplo.com', '/productos'));       // https://api.ejemplo.com:443/productos

  // required → el parámetro es obligatorio
// sin required → es opcional (necesita valor por defecto o ser nullable)
void configurarServidor({
  required String host,
  required int    puerto,
  bool   ssl        = true,
  int    timeoutSeg = 30,
}) {
  final protocolo = ssl ? 'https' : 'http';
  print('Conectando a $protocolo://$host:$puerto (timeout: ${timeoutSeg}s)');
}

void main() {
  // Los nombrados pueden pasarse en cualquier orden
  configurarServidor(
    host:       'db.miempresa.com',
    puerto:     5432,
    ssl:        false,
    timeoutSeg: 60,
  );

  // Solo los obligatorios — los opcionales toman su valor por defecto
  configurarServidor(
    host:   'api.miempresa.com',
    puerto: 443,
  );
}
}

