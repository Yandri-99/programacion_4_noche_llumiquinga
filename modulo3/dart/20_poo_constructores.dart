class Servidor {
  final String hostname;
  final String ip;
  final int    puerto;
  final bool   usaSsl;

  // Constructor principal
  Servidor({
    required this.hostname,
    required this.ip,
    required this.puerto,
    this.usaSsl = false,
  });

  // Constructor nombrado — alternativa de creación con configuración predefinida
  Servidor.local()
      : hostname = 'localhost',
        ip       = '127.0.0.1',
        puerto   = 8080,
        usaSsl   = false;

  Servidor.produccion({required this.hostname, required this.ip})
      : puerto  = 443,
        usaSsl  = true;

  // Constructor factory — lógica de creación más compleja
  factory Servidor.desdeUrl(String url) {
    // Analiza una URL y extrae sus partes
    final uri = Uri.parse(url);
    return Servidor(
      hostname: uri.host,
      ip:       uri.host,        // simplificado para el ejemplo
      puerto:   uri.port != 0 ? uri.port : (uri.scheme == 'https' ? 443 : 80),
      usaSsl:   uri.scheme == 'https',
    );
  }

  @override
  String toString() =>
      '${usaSsl ? "https" : "http"}://$hostname:$puerto';
}

void main() {
  final s1 = Servidor(hostname: 'api.mi-app.com', ip: '10.0.1.5', puerto: 3000);
  final s2 = Servidor.local();
  final s3 = Servidor.produccion(hostname: 'api.mi-app.com', ip: '10.0.1.5');
  final s4 = Servidor.desdeUrl('https://pagos.mi-app.com:8443/v1');

  print(s1);  // http://api.mi-app.com:3000
  print(s2);  // http://localhost:8080
  print(s3);  // https://api.mi-app.com:443
  print(s4);  // https://pagos.mi-app.com:8443
}