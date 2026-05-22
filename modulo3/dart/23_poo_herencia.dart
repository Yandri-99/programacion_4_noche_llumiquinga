// Clase base — comportamiento y datos comunes
class Animal {
  final String nombre;
  final int    edadAnios;

  Animal(this.nombre, this.edadAnios);

  // Método que cada subclase debe especializar
  String hacerSonido() => '...';

  // Método común — reutilizado sin cambios por todas las subclases
  void presentarse() {
    print('Soy $nombre, tengo $edadAnios años y hago: ${hacerSonido()}');
  }
}

// HERENCIA: Perro y Gato reutilizan Animal y lo especializan
class Perro extends Animal {
  Perro(super.nombre, super.edadAnios);

  @override
  String hacerSonido() => '¡Guau!';

  void buscarPelota() => print('$nombre busca la pelota 🎾');
}

class Gato extends Animal {
  Gato(super.nombre, super.edadAnios);

  @override
  String hacerSonido() => '¡Miau!';

  void trepar() => print('$nombre trepa al árbol 🌳');
}

void main() {
  final perro = Perro('Rex', 3);
  final gato  = Gato('Misu', 5);

  perro.presentarse();  // Soy Rex, tengo 3 años y hago: ¡Guau!
  gato.presentarse();   // Soy Misu, tengo 5 años y hago: ¡Miau!

  perro.buscarPelota();
  gato.trepar();
}