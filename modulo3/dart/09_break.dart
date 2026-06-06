void main() {
  // Escanear una matriz de servidores buscando uno disponible
  final datacenters = ['EU-WEST', 'EU-EAST', 'US-WEST'];
  final racks       = ['RACK-A', 'RACK-B', 'RACK-C'];
  String? servidorEncontrado;

  // La etiqueta marca el bucle exterior
  busqueda:
  for (final dc in datacenters) {
    for (final rack in racks) {
      final servidor = '$dc/$rack';
      print('Probando $servidor...');

      // Simular que EU-EAST/RACK-B está disponible
      if (dc == 'EU-EAST' && rack == 'RACK-B') {
        servidorEncontrado = servidor;
        break busqueda;  // sale de AMBOS bucles
      }
    }
  }
  print('Servidor asignado: $servidorEncontrado');
}