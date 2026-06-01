class Empleado {
  final String nombre;
  final int    edad;

  Empleado(this.nombre, this.edad);

  String trabajar() => '...';

  void presentarse() {
    print('Soy $nombre, tengo $edad años y trabajo como ${trabajar()}');
  }
}

class Conductor extends Empleado {
  Conductor(super.nombre, super.edad);

  @override
  String trabajar() => 'Conductor de bus';

  void conducirRuta() => print('$nombre conduce la ruta asignada');
}

class Supervisor extends Empleado {
  Supervisor(super.nombre, super.edad);

  @override
  String trabajar() => 'Supervisor de tráfico';

  void supervisarRuta() => print('$nombre supervisa las rutas en operación');
}

void main() {
  final conductor = Conductor('Carlos', 35);
  final supervisor = Supervisor('María', 40);

  conductor.presentarse();
  supervisor.presentarse();

  conductor.conducirRuta();
  supervisor.supervisarRuta();
}
