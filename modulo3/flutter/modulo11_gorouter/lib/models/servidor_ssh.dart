class ServidorSSH {
  final String id;
  final String nombre;
  final String ip;
  final int    puerto;
  final bool   ssl;

  const ServidorSSH({
    required this.id,
    required this.nombre,
    required this.ip,
    required this.puerto,
    required this.ssl,
  });
}

// Lista simulada — en una app real vendría de un provider
const servidoresSimulados = [
  ServidorSSH(id: '1', nombre: 'prod-web-01', ip: '10.0.2.10',   puerto: 22,   ssl: true),
  ServidorSSH(id: '2', nombre: 'prod-db-01',  ip: '10.0.2.20',   puerto: 22,   ssl: true),
  ServidorSSH(id: '3', nombre: 'staging-api', ip: '10.0.3.10',   puerto: 2222, ssl: false),
];