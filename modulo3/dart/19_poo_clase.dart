class Dispositivo {
  // 1. Propiedades
  final String id;
  final String nombre;
  String       ip;
  bool         _encendido = false;  // _ indica uso interno

  // 2. Constructor nombrado con parámetros nombrados
  Dispositivo({
    required this.id,
    required this.nombre,
    required this.ip,
  });

  // 3. Getter — propiedad derivada, solo lectura
  bool   get encendido => _encendido;
  String get estado    => _encendido ? 'activo' : 'inactivo';

  // 4. Setter — escritura controlada
  set estadoEncendido(bool valor) {
    _encendido = valor;
    print('$nombre: ${valor ? "encendido" : "apagado"}');
  }

  // 5. Métodos
  void conectar() {
    _encendido = true;
    print('$nombre conectado en $ip');
  }

  void desconectar() {
    _encendido = false;
    print('$nombre desconectado');
  }

  String resumen() => 'ID: $id | Nombre: $nombre | IP: $ip | Estado: $estado';

  // 6. toString
  @override
  String toString() => 'Dispositivo($nombre, $ip, $estado)';
}

void main() {
  // Crear una instancia
  final router = Dispositivo(
    id:     'DEV-001',
    nombre: 'router-principal',
    ip:     '192.168.1.1',
  );

  // Usar sus métodos y propiedades
  router.conectar();
  print(router.estado);       // activo
  print(router.resumen());
  print(router);              // llama toString() automáticamente

  router.estadoEncendido = false;  // usa el setter
  print(router.encendido);   // false
}